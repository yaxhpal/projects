/*
 * Copyright (c) 2009-2010, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice,
 *   this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * * Neither the name of Oracle nor the names of its contributors
 *   may be used to endorse or promote products derived from this software without
 *   specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

//
// callbacks.cc - Callback functions for UI events and actions
//

#include "FreewayWindow.h"
#include "traffic.h"
#include "about.h"

// Handler for "Load..." menu item
void 
file_load()
{
    GtkWidget *dialog = gtk_file_chooser_dialog_new("Freeway Load File",
            window.getShell(),
            GTK_FILE_CHOOSER_ACTION_OPEN,
            GTK_STOCK_OPEN, GTK_RESPONSE_ACCEPT,
            GTK_STOCK_CANCEL, GTK_RESPONSE_CANCEL,
            NULL);
    if (gtk_dialog_run(GTK_DIALOG(dialog)) == GTK_RESPONSE_ACCEPT) {
        char *filename = gtk_file_chooser_get_filename(GTK_FILE_CHOOSER(dialog));
        traffic_do_load(filename);
        g_free(filename);
        gtk_widget_destroy(dialog);
        show_message("File loaded successfully");
    } else {
        gtk_widget_destroy(dialog);
    }
}

// Handler for "Save" menu item
void 
file_save()
{
    if (!traffic_current_file) {
        file_saveas();
    } else {
        traffic_do_save(traffic_current_file);
        show_message("File saved successfully");
    }
}

// Handler for "Save As..." menu item
void 
file_saveas()
{
    GtkWidget *dialog = gtk_file_chooser_dialog_new("Freeway Save File",
            window.getShell(),
            GTK_FILE_CHOOSER_ACTION_OPEN,
            GTK_STOCK_SAVE, GTK_RESPONSE_ACCEPT,
            GTK_STOCK_CANCEL, GTK_RESPONSE_CANCEL,
            NULL);
    if (gtk_dialog_run(GTK_DIALOG(dialog)) == GTK_RESPONSE_ACCEPT) {
        gchar *filename = gtk_file_chooser_get_filename(GTK_FILE_CHOOSER(dialog));
        traffic_do_save(filename);
        g_free(filename);
        gtk_widget_destroy(dialog);
        show_message("File saved successfully");
    } else {
        gtk_widget_destroy(dialog);
    }
}

// Handler for "Close" menu item
void 
file_close()
{
    traffic_file_close();
}

// Handler for "About.." help-menu item
void 
help_about()
{
#if GTK_CHECK_VERSION(2, 12, 0)
    gtk_show_about_dialog(window.getShell(),
            "authors", "Gordon Prieur",
            "program-name", "GtkFreeway",
            "website", "http://developers.sun.com/sunstudio",
            "comments", "A GTK+ reimplementation of the Motif Freeway shipped with Sun Studio",
            NULL);
#else
    GtkWidget *dialog = gtk_dialog_new_with_buttons("About GtkFreeway", window.getShell(),
            GTK_DIALOG_DESTROY_WITH_PARENT, GTK_STOCK_OK, GTK_RESPONSE_NONE, NULL);
    GtkWidget *label = gtk_label_new("A GTK+ reimplementation of the Motif Freeway shipped with Sun Studio");
    g_signal_connect_swapped(dialog, "response", G_CALLBACK(gtk_widget_destroy), dialog);
    gtk_container_add(GTK_CONTAINER(GTK_DIALOG(dialog)->vbox), label);
    gtk_widget_show_all(dialog);
#endif
}

// Handler for `menu_reset (Reset)'
void 
reset_reset(GtkWidget *w, gpointer user_data )
{
    traffic_reset();
}

// Handler for `menu_reset (Clear Wrecks)
void 
reset_clear(GtkWidget *w, gpointer user_data )
{
    traffic_clear();
}

// Callback function for `gap'
void 
gap_change(GtkWidget *w, gpointer user_data )
{
    traffic_gap(gtk_range_get_value(GTK_RANGE(w)));
}

void 
time_change(GtkWidget *w, gpointer user_data )
{
    traffic_time(gtk_range_get_value(GTK_RANGE(w)));
}

void 
fwy_start()
{
    traffic_start();
}

// Callback function for `button_stop'
void 
fwy_stop()
{
    traffic_stop();
}

// Callback function for `choice_randomize'
void 
randx(GtkWidget *w, gpointer user_data)
{
    if (gtk_toggle_button_get_active(GTK_TOGGLE_BUTTON(w))) {
        traffic_randomize(GPOINTER_TO_INT(user_data));
    }
}

// Callback function for setting speed zones.
void 
zone_speed(GtkWidget *w, gpointer user_data )
{
    int zone = GPOINTER_TO_INT(user_data) - 1;
    int i = gtk_combo_box_get_active(GTK_COMBO_BOX(w));
    
    if (zone >= 0 && zone < NZONES && i >= 0 && i < NSPEEDS) {
        traffic_speed(zone, atoi(SPEED_STR[i]));
    }
}

// Callback for "Close" button on Help popup
void 
help_close (GtkWidget *w, gpointer user_data )
{
//	XtUnmapWidget(window.help_winp->shell);
}

// Callback for Freeway "Quit" button
void 
fw_quit()
{
    traffic_stop();
    gtk_main_quit();
}

// Callback for Freeway "Quit" button
void 
popup_destroyed(GtkWidget *w, gpointer user_data )
{
    // A user has used the window-system's popup-window menu
    // to dismiss the window. This cuases the window to be destroyed
    // since the XmdeleteResponse resource was sent to XmDESTROY
    // to avoid the strange behavior of different window managers.
    // Figure-out which window was destroyed and reinitialize it.

//	if (w == window.file_winp->shell) {
//		window.file_winp->objects_initialize(&window);
//	} else if (w == window.help_winp->shell) {
//		window.help_winp->objects_initialize(&window);
//	} else if (w == window.vinfo_winp->shell){
//		window.vinfo_winp->objects_initialize(&window);
//	}
}

void
show_message(const gchar* message)
{
    GtkWidget *dialog = gtk_message_dialog_new(
            window.getShell(),
            GTK_DIALOG_DESTROY_WITH_PARENT,
            GTK_MESSAGE_INFO,
            GTK_BUTTONS_OK,
            message);
    gtk_dialog_run(GTK_DIALOG(dialog));
    gtk_widget_destroy(dialog);
}
