#!/usr/bin/perl

#  This script loops through each overdue item, determines the fine,
#  and updates the total amount of fines due by each user.  It relies on
#  the existence of /tmp/fines, which is created by ???
# Doesnt really rely on it, it relys on being able to write to /tmp/
# It creates the fines file
#
#  This script is meant to be run nightly out of cron.

# Copyright 2000-2002 Katipo Communications
#
# This file is part of Koha.
#
# Koha is free software; you can redistribute it and/or modify it under the
# terms of the GNU General Public License as published by the Free Software
# Foundation; either version 2 of the License, or (at your option) any later
# version.
#
# Koha is distributed in the hope that it will be useful, but WITHOUT ANY
# WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
# A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License along
# with Koha; if not, write to the Free Software Foundation, Inc.,
# 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.


# FIXME: use FinesMode as described or change syspref description
use strict;
#use warnings; FIXME - Bug 2505

BEGIN {
    # find Koha's Perl modules
    # test carefully before changing this
    use FindBin;
    eval { require "$FindBin::Bin/kohalib.pl" };
}

use Date::Calc qw/Date_to_Days/;

use C4::Context;
use C4::Circulation;
use C4::Overdues;
use C4::Calendar qw();  # don't need any exports from Calendar
use C4::Biblio;
use C4::Debug;  # supplying $debug and $cgi_debug
use Getopt::Long;
use Data::Dumper;

my $help = 0;
my $verbose = 0;
my $output_dir;

GetOptions( 'h|help'    => \$help,
            'v|verbose' => \$verbose,
            'o|out:s'   => \$output_dir,
       );
my $usage = << 'ENDUSAGE';

This script calculates and charges overdue fines
to patron accounts.  If the Koha System Preference
'finesMode' is set to 'production', the fines are charged
to the patron accounts.  If set to 'test', the fines are
calculated but not applied.

This script has the following parameters :
    -h --help: this message
    -o --out:  ouput directory for logs (defaults to env or /tmp if !exist)
    -v --verbose

ENDUSAGE

die $usage if $help;

use vars qw(@borrower_fields @item_fields @other_fields);
use vars qw($fldir $libname $control $mode $delim $dbname $today $today_iso $today_days);
use vars qw($filename);

CHECK {
    @borrower_fields = qw(cardnumber categorycode surname firstname email phone address citystate);
        @item_fields = qw(itemnumber barcode date_due);
       @other_fields = qw(type days_overdue fine);
    $libname = C4::Context->preference('LibraryName');
    $control = C4::Context->preference('CircControl');
    $mode    = C4::Context->preference('finesMode');
    $dbname  = C4::Context->config('database');
    $delim   = "\t"; # ?  C4::Context->preference('delimiter') || "\t";

}

INIT {
    $debug and print "Each line will contain the following fields:\n",
        "From borrowers : ", join(', ', @borrower_fields), "\n",
        "From items : ", join(', ', @item_fields), "\n",
        "Per overdue: ", join(', ', @other_fields), "\n",
        "Delimiter: '$delim'\n";
}

my $data = Getoverdues();
my $overdueItemsCounted = 0;
my %calendars = ();
$today = C4::Dates->new();
$today_iso = $today->output('iso');
$today_days = Date_to_Days(split(/-/,$today_iso));
if($output_dir){
    $fldir = $output_dir if( -d $output_dir );
} else {
    $fldir = $ENV{TMPDIR} || "/tmp";
}
if (!-d $fldir) {
    warn "Could not write to $fldir ... does not exist!";
}
$filename = $dbname;
$filename =~ s/\W//;
$filename = $fldir . '/'. $filename . '_test_' .  $today_iso . ".log";
print "writing to $filename\n" if $verbose;
open (FILE, ">$filename") or die "Cannot write file $filename: $!";
print FILE join $delim, (@borrower_fields, @item_fields, @other_fields);
print FILE "\n";

print FILE "Number of overdues pan India:\t" , scalar(@$data);
my $count_for_cl = 0;
for (my $i=0; $i< 5  $i++) {
	if ($data->[$i]->{'homebranch'} eq 'CL') {
		$count_for_cl++;
	print FILE "IN THE LOOP Count [$count_for_cl] \n\n";
    my $datedue = C4::Dates->new($data->[$i]->{'date_due'},'iso');
    my $datedue_days = Date_to_Days(split(/-/,$datedue->output('iso')));
    my $due_str = $datedue->output();
    unless (defined $data->[$i]->{'borrowernumber'}) {
        print STDERR "ERROR in Getoverdues line $i: issues.borrowernumber IS NULL.  Repair 'issues' table now!  Skipping record.\n";
        next;   # Note: this doesn't solve everything.  After NULL borrowernumber, multiple issues w/ real borrowernumbers can pile up.
    }
    my $borrower = BorType($data->[$i]->{'borrowernumber'});
    my $branchcode = ($control eq 'ItemHomeLibrary') ? $data->[$i]->{homebranch} :
                     ($control eq 'PatronLibrary'  ) ?   $borrower->{branchcode} :
                                                       $data->[$i]->{branchcode} ;
    # In final case, CircControl must be PickupLibrary. (branchcode comes from issues table here).
    my $calendar;
    unless (defined ($calendars{$branchcode})) {
        $calendars{$branchcode} = C4::Calendar->new(branchcode => $branchcode);
    }
    $calendar = $calendars{$branchcode};
    my $isHoliday = $calendar->isHoliday(split '/', $today->output('metric'));
      
    ($datedue_days <= $today_days) or next; # or it's not overdue, right?

    $overdueItemsCounted++;
    my ($amount,$type,$daycounttotal,$daycount)=
                CalcFine($data->[$i], $borrower->{'categorycode'}, $branchcode,undef,undef, $datedue, $today);
                
        
        print FILE "($count_for_cl) Returns by CalcFine: Amount: ",$amount," Type: ",$type, " Day count total: ",$daycounttotal, " Day count: ",$daycount," Borrower Number", $borrower->{borrowernumber} ,"\n\n";
        
        if($amount <= 0) {
         	print FILE "Date Due:: ", $datedue->output('iso'),"\n";
         	print FILE "TODAY DATE:: ", $today->output('iso'),"\n";
         	print FILE "Cat code of borrower :: $borrower->{'categorycode'}\n\n";
         	print FILE "Branch code of borrower:: $branchcode\n\n";
         	
         }
        
        
        # FIXME: $type NEVER gets populated by anything.
    (defined $type) or $type = '';
        # Don't update the fine if today is a holiday.  
        # This ensures that dropbox mode will remove the correct amount of fine.
        print FILE "MODE::",$mode,"\t","IS HOLIDAY::","$isHoliday","\n";
        if ($mode eq 'production' and  ! $isHoliday) {
        	print FILE "Data struc:: \n" , Dumper $data->[$i],"\n";
        	print FILE "Borrower Number:: $data->[$i]->{'borrowernumber'} \n";
        	print FILE "Amount $amount \n\n";
        	print FILE "DUE STR :: $due_str\n\n";
                UpdateFineTest($data->[$i]->{'itemnumber'},$data->[$i]->{'borrowernumber'},$amount,$type,$due_str) if( $amount > 0 ) ;
        }
#    my @cells = ();
#    push @cells, map {$borrower->{$_}} @borrower_fields;
#    push @cells, map {$data->[$i]->{$_}} @item_fields;
#    push @cells, $type, $daycounttotal, $amount;
#    print FILE join($delim, @cells), "\n";

}
}
my $numOverdueItems = scalar(@$data);
if ($verbose) {
   print <<EOM;
Fines assessment -- $today_iso -- Saved to $filename
Number of Overdue Items:
     counted $overdueItemsCounted
    reported $numOverdueItems

EOM
}

close FILE;


sub UpdateFineTest {
	print "I AM IN UpdateFineTest\n\n";
    my ( $itemnum, $borrowernumber, $amount, $type, $due ) = @_;
	$debug and warn "UpdateFineTest ($itemnum, $borrowernumber, $amount, " . ($type||'""') . ", $due) called";
    my $dbh = C4::Context->dbh;
    # FIXME - What exactly is this query supposed to do? It looks up an
    # entry in accountlines that matches the given item and borrower
    # numbers, where the description contains $due, and where the
    # account type has one of several values, but what does this _mean_?
    # Does it look up existing fines for this item?
    # FIXME - What are these various account types? ("FU", "O", "F", "M")
	#	"L"   is LOST item
	#   "A"   is Account Management Fee
	#   "N"   is New Card
	#   "M"   is Sundry
	#   "O"   is Overdue ??
	#   "F"   is Fine ??
	#   "FU"  is Fine UPDATE??
	#	"Pay" is Payment
	#   "REF" is Cash Refund
    my $sth = $dbh->prepare(
        "SELECT * FROM accountlines
		WHERE itemnumber=?
		AND   borrowernumber=?
		AND   accounttype IN ('FU','O','F','M')
		AND   description like ? "
    );
    $sth->execute( $itemnum, $borrowernumber, "%$due%" );

    if ( my $data = $sth->fetchrow_hashref ) {

		# we're updating an existing fine.  Only modify if we're adding to the charge.
        # Note that in the current implementation, you cannot pay against an accruing fine
        # (i.e. , of accounttype 'FU').  Doing so will break accrual.
    	if ( $data->{'amount'} != $amount ) {
            my $diff = $amount - $data->{'amount'};
            $diff = 0 if ( $data->{amount} > $amount);
            my $out  = $data->{'amountoutstanding'} + $diff;
            my $query = "
                UPDATE accountlines
				SET date=now(), amount=?, amountoutstanding=?,
					lastincrement=?, accounttype='FU'
	  			WHERE borrowernumber=?
				AND   itemnumber=?
				AND   accounttype IN ('FU','O')
				AND   description LIKE ?
				LIMIT 1 ";
            my $sth2 = $dbh->prepare($query);
			# FIXME: BOGUS query cannot ensure uniqueness w/ LIKE %x% !!!
			# 		LIMIT 1 added to prevent multiple affected lines
			# FIXME: accountlines table needs unique key!! Possibly a combo of borrowernumber and accountline.  
			# 		But actually, we should just have a regular autoincrementing PK and forget accountline,
			# 		including the bogus getnextaccountno function (doesn't prevent conflict on simultaneous ops).
			# FIXME: Why only 2 account types here?
			$debug and print STDERR "UpdateFine query: $query\n" .
				"w/ args: $amount, $out, $diff, $data->{'borrowernumber'}, $data->{'itemnumber'}, \"\%$due\%\"\n";
            $sth2->execute($amount, $out, $diff, $data->{'borrowernumber'}, $data->{'itemnumber'}, "%$due%");
        } else {
            #      print "no update needed $data->{'amount'}"
        }
    } else {
        my $sth4 = $dbh->prepare(
            "SELECT title FROM biblio LEFT JOIN items ON biblio.biblionumber=items.biblionumber WHERE items.itemnumber=?"
        );
        $sth4->execute($itemnum);
        my $title = $sth4->fetchrow;

#         #   print "not in account";
#         my $sth3 = $dbh->prepare("Select max(accountno) from accountlines");
#         $sth3->execute;
# 
#         # FIXME - Make $accountno a scalar.
#         my @accountno = $sth3->fetchrow_array;
#         $sth3->finish;
#         $accountno[0]++;
# begin transaction
		my $nextaccntno = C4::Accounts::getnextacctno($borrowernumber);
		my $desc = ($type ? "$type " : '') . "$title $due";	# FIXEDME, avoid whitespace prefix on empty $type
		my $query = "INSERT INTO accountlines
		    (borrowernumber,itemnumber,date,amount,description,accounttype,amountoutstanding,lastincrement,accountno)
			    VALUES (?,?,now(),?,?,'FU',?,?,?)";
		my $sth2 = $dbh->prepare($query);
		$debug and print STDERR "UpdateFine query: $query\nw/ args: $borrowernumber, $itemnum, $amount, $desc, $amount, $amount, $nextaccntno\n";
#        $sth2->execute($borrowernumber, $itemnum, $amount, $desc, $amount, $amount, $nextaccntno);
    }
    # logging action
    &logaction(
        "FINES",
        $type,
        $borrowernumber,
        "due=".$due."  amount=".$amount." itemnumber=".$itemnum
        ) if C4::Context->preference("FinesLog");
}
