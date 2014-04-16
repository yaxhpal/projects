-- MySQL dump 10.13  Distrib 5.1.73, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: hulk
-- ------------------------------------------------------
-- Server version	5.1.73-0ubuntu0.10.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `{{storage}}`
--

DROP TABLE IF EXISTS `{{storage}}`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `{{storage}}` (
  `key` varchar(255) DEFAULT NULL,
  `value` text
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `applied_jobs`
--

DROP TABLE IF EXISTS `applied_jobs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `applied_jobs` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id of the applied jobs',
  `usr_id` bigint(20) unsigned DEFAULT NULL COMMENT 'the candidate who is applied;referring to the id of the user table',
  `job_id` bigint(20) unsigned DEFAULT NULL COMMENT 'tells to which job candidate applied;referring the id of the jobs table',
  `profile_id` bigint(20) unsigned DEFAULT NULL COMMENT 'which profile candidate used while applying  for the job',
  `field1` varchar(64) DEFAULT NULL,
  `field2` varchar(64) DEFAULT NULL,
  `deleted` char(1) NOT NULL COMMENT 'Show the status of the record;Y for delete and N for Not delete',
  `updated` int(11) NOT NULL COMMENT 'Modified Time of this record',
  `created` int(11) NOT NULL COMMENT 'created time',
  `createdby` bigint(20) NOT NULL COMMENT 'Shows who is created this record',
  `updatedby` bigint(20) NOT NULL COMMENT 'shows who is modified this record\n',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `FK_APPLIEDJOB_USER_CANDIDATEID_idx` (`usr_id`),
  KEY `FK_APPLIEDJOB_JOBS_JOBID_idx` (`job_id`),
  KEY `FK_APPLIEDJOB_PROFILE_PROFILEID_idx` (`profile_id`),
  CONSTRAINT `FK_APPLIEDJOB_JOBS_JOBID` FOREIGN KEY (`job_id`) REFERENCES `jobs` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_APPLIEDJOB_PROFILE_PROFILEID` FOREIGN KEY (`profile_id`) REFERENCES `cand_resume` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_APPLIEDJOB_USER_CANDIDATEID` FOREIGN KEY (`usr_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=94 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cand`
--

DROP TABLE IF EXISTS `cand`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cand` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id of the candidate information',
  `user_id` bigint(20) unsigned NOT NULL COMMENT 'candidate id referring to id of the user table',
  `headline` text,
  `current_sector` bigint(20) unsigned DEFAULT NULL COMMENT 'candidate''s current sector.. referring to master table id',
  `current_location` bigint(20) unsigned DEFAULT NULL COMMENT 'Current Location of the candidate',
  `current_salary` double DEFAULT NULL COMMENT 'Present Salary of the user on yearly basis',
  `current_jobtype` bigint(20) unsigned DEFAULT NULL COMMENT 'candidate''s current job type like part time , full time etc..',
  `expected_salary_min` double unsigned DEFAULT NULL COMMENT 'in case no min max feature is there, store expected salary in min field. In max field put default as 0',
  `expected_salary_max` double unsigned DEFAULT NULL COMMENT 'Expected Salary',
  `required_jobtype` bigint(20) unsigned DEFAULT NULL COMMENT 'desired type of job like contract or permenant etc',
  `work_permit_type` char(1) DEFAULT NULL COMMENT 'Work permit type. H = H1 Visa required, B = B1 Visa required etc, Y= Yes, N=No',
  `overview` text COMMENT 'Overview of the candidate',
  `contact` bigint(20) unsigned DEFAULT NULL COMMENT 'contact reference of the candidate',
  `key_skills` varchar(2048) DEFAULT NULL COMMENT 'Skillsets of the candidate',
  `current_designation` varchar(256) DEFAULT NULL,
  `deleted` char(1) NOT NULL COMMENT 'Show the status of the record;Y for delete and N for Not delete',
  `created` int(11) NOT NULL COMMENT 'Created Time',
  `updated` int(11) NOT NULL COMMENT 'Updated Time',
  `createdby` bigint(20) NOT NULL COMMENT 'Who is Created this record',
  `updatedby` bigint(20) NOT NULL COMMENT 'Who is updated this Record\n',
  `salary_confidential` char(1) DEFAULT NULL COMMENT 'Y  for confidential and N for non confidential\n',
  `experience` bigint(20) unsigned DEFAULT NULL COMMENT 'Experience',
  `profile_photo` varchar(1024) DEFAULT NULL COMMENT 'Profile Photo',
  `organization` bigint(20) unsigned DEFAULT NULL COMMENT 'Current Organization',
  `job_preference` bigint(19) unsigned DEFAULT NULL,
  `completion_score` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_CURRENT_SECTOR_idx` (`current_sector`),
  KEY `FK_CURRENT_LOC_idx` (`current_location`),
  KEY `FK_CURRENT_JOB_TYPE_idx` (`current_jobtype`),
  KEY `FK_REQ_JOB_TYPE_idx` (`required_jobtype`),
  KEY `FK_PROFILE_USER_USERID` (`user_id`),
  KEY `FK_PROFILE_CND_SECTOR` (`current_sector`),
  KEY `FK_PROFILE_CND_LOC` (`current_location`),
  KEY `FK_PROFILE_CND_JOBTYPE` (`current_jobtype`),
  KEY `FK_PROFILE_CND_EXP` (`experience`),
  KEY `FK_PROFILE_CONTACT_CONTACTID` (`contact`),
  KEY `FK_PROFILE_COMPANY_ORGANIZATION` (`organization`),
  KEY `FK_PROFILE_CND_JOBPREFERENCES` (`job_preference`),
  KEY `FK_PROFILE_CONTACT_CONTACT` (`contact`),
  CONSTRAINT `FK_PROFILE_CND_EXP` FOREIGN KEY (`experience`) REFERENCES `cnd` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_PROFILE_CND_JOBPREFERENCES` FOREIGN KEY (`job_preference`) REFERENCES `cnd` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_PROFILE_CND_JOBTYPE` FOREIGN KEY (`current_jobtype`) REFERENCES `cnd` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_PROFILE_CND_LOC` FOREIGN KEY (`current_location`) REFERENCES `cnd` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_PROFILE_CND_SECTOR` FOREIGN KEY (`current_sector`) REFERENCES `cnd` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_PROFILE_COMPANY_ORGANIZATION` FOREIGN KEY (`organization`) REFERENCES `company_details` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_PROFILE_CONTACT_CONTACT` FOREIGN KEY (`contact`) REFERENCES `contact` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_PROFILE_MASTER_CURRENTJOBTYPE` FOREIGN KEY (`current_jobtype`) REFERENCES `cnd` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_PROFILE_MASTER_REQJOBTYPE` FOREIGN KEY (`required_jobtype`) REFERENCES `cnd` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_PROFILE_USER_USERID` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8 COMMENT='Candidate various profile need to be stored here. In case us';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cand_certification`
--

DROP TABLE IF EXISTS `cand_certification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cand_certification` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'unique id corresponding to candidate''s  each professional qualification',
  `usr_id` bigint(20) unsigned NOT NULL COMMENT 'User id corresponding to this record',
  `title` varchar(256) DEFAULT NULL COMMENT 'title of the qualification',
  `certification` varchar(256) DEFAULT NULL COMMENT 'cerification name',
  `certification_authority` varchar(256) DEFAULT NULL COMMENT 'certification authority',
  `from` int(11) DEFAULT NULL COMMENT 'starting period',
  `to` int(11) DEFAULT NULL COMMENT 'ending period',
  `description` text COMMENT 'Description of the qualification',
  `deleted` char(1) NOT NULL COMMENT 'Shows current status of the record;Y for delete and N for not delete',
  `created` int(11) NOT NULL COMMENT 'Created Time',
  `updated` int(11) NOT NULL COMMENT 'Modified Time of the Record',
  `createdby` bigint(20) NOT NULL COMMENT 'Shows who is created this record',
  `updatedby` bigint(20) NOT NULL COMMENT 'Shows who is updated this record',
  `professional_association` text COMMENT 'Professional Association of the candidate',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `FK_QUALI__PROFILE_PROFILEID_idx` (`usr_id`),
  CONSTRAINT `FK_QUALI__USER_USRID` FOREIGN KEY (`usr_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cand_education`
--

DROP TABLE IF EXISTS `cand_education`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cand_education` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'unique id corresponding to candidate''s education',
  `usr_id` bigint(20) unsigned NOT NULL COMMENT 'User id of the candidate to which this record(education info) belongs',
  `title` varchar(256) DEFAULT NULL COMMENT 'title of the course',
  `label` bigint(20) unsigned DEFAULT NULL COMMENT 'Label or type of the course he attended, Graduate, PG',
  `institution` bigint(20) unsigned DEFAULT NULL COMMENT 'THe institution in which he studied',
  `from` int(11) DEFAULT NULL COMMENT 'course started date.. Unix time stamp',
  `to` int(11) DEFAULT NULL COMMENT 'course ended date',
  `deleted` char(1) NOT NULL COMMENT 'Shows current status of the record;Y for delete and N for not delete',
  `created` int(11) NOT NULL COMMENT 'CREATED TIME OF THE RECORD',
  `updated` int(11) NOT NULL COMMENT 'updated time of the record',
  `createdby` bigint(20) NOT NULL COMMENT 'who is created this record',
  `updatedby` bigint(20) NOT NULL COMMENT 'updated time of this record',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `FK_MASTER_COURSE_TYPE_idx` (`label`),
  KEY `FK_MASTER_INSTITUTION_idx` (`institution`),
  KEY `FK_EDU_PROFILE_PROFILEID_idx` (`usr_id`),
  CONSTRAINT `FK_EDU_MASTER_COURSE_TYPE` FOREIGN KEY (`label`) REFERENCES `cnd` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_EDU_MASTER_INSTITUTION` FOREIGN KEY (`institution`) REFERENCES `cnd` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_EDU_user_userid` FOREIGN KEY (`usr_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cand_experience`
--

DROP TABLE IF EXISTS `cand_experience`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cand_experience` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'indicates unique id corresponding to candidate''s  each work  experience',
  `usr_id` bigint(20) unsigned NOT NULL COMMENT 'indicates this work experience belong  to which user',
  `title` varchar(256) DEFAULT NULL COMMENT 'Tilte ',
  `company` bigint(20) unsigned DEFAULT NULL COMMENT 'Company in which candidate worked',
  `location` bigint(20) unsigned DEFAULT NULL COMMENT 'Location Of the company where candidate worked',
  `from` int(11) DEFAULT NULL COMMENT 'work started ',
  `to` int(11) DEFAULT NULL COMMENT 'Work ended in this company',
  `description` text COMMENT 'detailsof candidate''s work in this company',
  `deleted` char(1) NOT NULL COMMENT 'Show the status of the record;Y for delete and N for Not delete',
  `created` int(11) NOT NULL,
  `updated` int(11) NOT NULL COMMENT 'Modified Time of this record',
  `createdby` bigint(20) NOT NULL COMMENT 'Shows who is created this record',
  `updatedby` varchar(45) NOT NULL COMMENT 'shows who is modified this record\n',
  `functional_area` bigint(20) unsigned DEFAULT NULL,
  `key_skills` varchar(2048) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `FK_EXP_MASTER_COMPANY_idx` (`company`),
  KEY `FK_EXP_MASTER_LOCATION_idx` (`location`),
  KEY `FK_EXP_PROFILE_PROFILEID_idx` (`usr_id`),
  KEY `FK_EXP_CND_FUNCTIONS` (`functional_area`),
  CONSTRAINT `FK_EXP_CND_FUNCTIONS` FOREIGN KEY (`functional_area`) REFERENCES `cnd` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_EXP_MASTER_COMPANY` FOREIGN KEY (`company`) REFERENCES `company_details` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_EXP_MASTER_LOCATION` FOREIGN KEY (`location`) REFERENCES `cnd` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_EXP_USER_USRID` FOREIGN KEY (`usr_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cand_jobs_relevancy`
--

DROP TABLE IF EXISTS `cand_jobs_relevancy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cand_jobs_relevancy` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id of the  saved jobs',
  `usr_id` bigint(20) unsigned DEFAULT NULL COMMENT 'the candidate who is saved this job ;referring to the id of the user table',
  `job_id` bigint(20) unsigned DEFAULT NULL COMMENT 'tells which job candidate is saved ;referring the id of the jobs table',
  `field1` varchar(64) DEFAULT NULL,
  `field2` varchar(64) DEFAULT NULL,
  `relevant` char(1) NOT NULL COMMENT 'Show the status of the record;Y for delete and N for Not delete',
  `created` int(11) NOT NULL,
  `updated` int(11) NOT NULL COMMENT 'Modified Time of this record',
  `createdby` bigint(20) NOT NULL COMMENT 'Shows who is created this record',
  `updatedby` bigint(20) NOT NULL COMMENT 'shows who is modified this record\n',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `FK_SAVEDJOBS_USER_CANDIDATEID_idx` (`usr_id`),
  KEY `FK_SAVEDJOBS_JOBS_JOBSID_idx` (`job_id`),
  CONSTRAINT `FK_JOBSREL_JOBS_JOBSID` FOREIGN KEY (`job_id`) REFERENCES `jobs` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_JOBSREL_USER_CANDIDATEID` FOREIGN KEY (`usr_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cand_pref_location`
--

DROP TABLE IF EXISTS `cand_pref_location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cand_pref_location` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'unique id of each preferred location  of the candidate ',
  `cand_profile_id` bigint(20) unsigned NOT NULL COMMENT 'indicates this information belong to which candidate',
  `cnd_pref_location` bigint(20) unsigned NOT NULL COMMENT 'Preferred Location of the candidate',
  `deleted` varchar(45) DEFAULT NULL COMMENT 'Show the status of the record;Y for delete and N for Not delete',
  `created` varchar(45) DEFAULT NULL,
  `updated` varchar(45) DEFAULT NULL COMMENT 'Modified Time of this record',
  `createdby` varchar(45) DEFAULT NULL COMMENT 'Shows who is created this record',
  `updatedby` varchar(45) DEFAULT NULL COMMENT 'shows who is modified this record\n',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `FK_PREFLOC_MASTER_PREFLOC_idx` (`cnd_pref_location`),
  KEY `FK_PREFLOC_PROFILE_PROFILEID_idx` (`cand_profile_id`),
  CONSTRAINT `FK_PREFLOC_MASTER_PREFLOC` FOREIGN KEY (`cnd_pref_location`) REFERENCES `cnd` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_PREFLOC_PROFILE_PROFILEID` FOREIGN KEY (`cand_profile_id`) REFERENCES `cand` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Preferred location of candidate';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cand_preferences`
--

DROP TABLE IF EXISTS `cand_preferences`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cand_preferences` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'preference if for each candidate''s preferences',
  `usr_id` bigint(20) unsigned NOT NULL COMMENT 'indicates this preference belong to which candidate',
  `cnd_preference_type` bigint(20) unsigned NOT NULL COMMENT 'Type of preference .master table contains all type pf preferences',
  `deleted` char(1) DEFAULT NULL COMMENT 'Show the status of the record;Y for delete and N for Not delete',
  `created` int(11) DEFAULT NULL,
  `updated` int(11) DEFAULT NULL COMMENT 'Modified Time of this record',
  `createdby` bigint(20) DEFAULT NULL COMMENT 'Shows who is created this record',
  `updatedby` varchar(45) DEFAULT NULL COMMENT 'shows who is modified this record\n',
  PRIMARY KEY (`id`),
  KEY `FK_PREF_MASTER_PREFERENCETYPE_idx` (`cnd_preference_type`),
  KEY `FK_PREF__USER_CANDIDATEID_idx` (`usr_id`),
  CONSTRAINT `FK_PREF_MASTER_PREFERENCETYPE` FOREIGN KEY (`cnd_preference_type`) REFERENCES `cnd` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_PREF__USER_CANDIDATEID` FOREIGN KEY (`usr_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8 COMMENT='We are storing all the preferences of users in this table';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cand_resume`
--

DROP TABLE IF EXISTS `cand_resume`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cand_resume` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id of the candidate information',
  `user_id` bigint(20) unsigned NOT NULL COMMENT 'candidate id referring to id of the user table',
  `resume_title` varchar(256) DEFAULT NULL COMMENT 'Title or head line the profile',
  `resume` varchar(1024) DEFAULT NULL,
  `coverletter_title` varchar(256) DEFAULT NULL,
  `coverletter` text,
  `status` char(1) NOT NULL DEFAULT 'A' COMMENT 'Status of the profile  ;A for active , I for inactine, D= Default profile default A',
  `sector` bigint(20) unsigned DEFAULT NULL COMMENT 'candidate''s current sector.. referring to master table id',
  `preferred_location` bigint(20) unsigned DEFAULT NULL COMMENT 'Current Location of the candidate',
  `key_skills` varchar(2048) DEFAULT NULL COMMENT 'Skillsets of the candidate',
  `experience` bigint(20) unsigned DEFAULT NULL COMMENT 'Experience',
  `deleted` char(1) NOT NULL COMMENT 'Show the status of the record;Y for delete and N for Not delete',
  `created` int(11) NOT NULL COMMENT 'Created Time',
  `updated` int(11) NOT NULL COMMENT 'Updated Time',
  `createdby` bigint(20) NOT NULL COMMENT 'Who is Created this record',
  `updatedby` bigint(20) NOT NULL COMMENT 'Who is updated this Record\n',
  PRIMARY KEY (`id`),
  KEY `FK_RESUME_USER_USERID` (`user_id`),
  KEY `FK_RESUME_CND_LOC` (`preferred_location`),
  KEY `FK_RESUME_CND_EXP` (`experience`),
  KEY `FK_RESUME_CND_SECTOR` (`sector`),
  CONSTRAINT `FK_RESUME_CND_EXP` FOREIGN KEY (`experience`) REFERENCES `cnd` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_RESUME_CND_LOC` FOREIGN KEY (`preferred_location`) REFERENCES `cnd` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_RESUME_CND_SECTOR` FOREIGN KEY (`sector`) REFERENCES `cnd` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_RESUME_USER_USERID` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8 COMMENT='Candidate  profile need to be stored here. ';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cnd`
--

DROP TABLE IF EXISTS `cnd`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cnd` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'unique id corresponding to each master entry',
  `name` varchar(256) NOT NULL COMMENT 'name',
  `description` varchar(256) DEFAULT NULL COMMENT 'full desription of the specefic master entry',
  `group_name` varchar(256) NOT NULL COMMENT 'indicates in which group this master entry belongs',
  `parent` bigint(20) unsigned DEFAULT NULL COMMENT 'points to the parent of this entry.\nif no parent exist null',
  `sequence` int(11) DEFAULT NULL COMMENT ' sequence no  help to sort the entry in a desired order',
  `option1` varchar(2048) DEFAULT NULL COMMENT 'option',
  `option2` varchar(64) DEFAULT NULL COMMENT 'option 2',
  `deleted` char(1) NOT NULL COMMENT 'Show the status of the record;Y for delete and N for Not delete',
  `created` int(11) NOT NULL,
  `updated` int(11) NOT NULL COMMENT 'Modified Time of this record',
  `createdby` bigint(20) NOT NULL COMMENT 'Shows who is created this record',
  `updatedby` bigint(20) NOT NULL COMMENT 'shows who is modified this record\n',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22314 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `company_details`
--

DROP TABLE IF EXISTS `company_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `company_details` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id of the company',
  `company_name` varchar(512) DEFAULT NULL COMMENT 'name of the company',
  `company_type` bigint(20) unsigned DEFAULT NULL COMMENT 'type of the company',
  `location` bigint(20) unsigned DEFAULT NULL COMMENT 'location of the company. Need to be discussed',
  `contact` bigint(20) unsigned DEFAULT NULL COMMENT 'contact reference of the company',
  `overview` text COMMENT 'overview of the company',
  `logo` varchar(64) DEFAULT NULL COMMENT 'logo name of the company',
  `logo_1` varchar(64) DEFAULT NULL COMMENT 'additional logo',
  `rejection_reason` varchar(1024) DEFAULT NULL COMMENT 'If company is rejected then reason',
  `deleted` char(1) NOT NULL COMMENT 'Show the status of the record;Y for delete and N for Not delete',
  `created` int(11) NOT NULL,
  `updated` int(11) NOT NULL COMMENT 'Modified Time of this record',
  `createdby` bigint(20) NOT NULL COMMENT 'Shows who is created this record',
  `updatedby` bigint(20) NOT NULL COMMENT 'shows who is modified this record\n',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `FK_COMP_MASTER_COMPYTPE_idx` (`company_type`),
  KEY `FK_COMP_MASTER_LOC_idx` (`location`),
  KEY `FK_COMP_CONTACT_CONTACT_idx` (`contact`),
  CONSTRAINT `FK_COMP_CONTACT_CONTACT` FOREIGN KEY (`contact`) REFERENCES `contact` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_COMP_MASTER_COMPYTPE` FOREIGN KEY (`company_type`) REFERENCES `cnd` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_COMP_MASTER_LOC` FOREIGN KEY (`location`) REFERENCES `cnd` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `contact`
--

DROP TABLE IF EXISTS `contact`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contact` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Contact id of each user',
  `phone` varchar(64) DEFAULT NULL COMMENT 'telephone no',
  `mobile` varchar(64) DEFAULT NULL COMMENT 'mobile no',
  `email` varchar(64) DEFAULT NULL COMMENT 'email address',
  `address` varchar(256) DEFAULT NULL COMMENT 'mail address',
  `fax` varchar(45) DEFAULT NULL COMMENT 'fax no.',
  `linkedin` varchar(256) DEFAULT NULL COMMENT 'linkedin link',
  `facebook` varchar(256) DEFAULT NULL COMMENT 'facebook contact link',
  `twitter` varchar(256) DEFAULT NULL COMMENT 'twitter link',
  `google` varchar(256) DEFAULT NULL COMMENT 'google+ link',
  `website` varchar(256) DEFAULT NULL COMMENT 'website address',
  `blog` varchar(256) DEFAULT NULL COMMENT 'blog address',
  `other` varchar(256) DEFAULT NULL COMMENT 'other way of contact',
  `contactType` char(1) DEFAULT NULL COMMENT 'Contact type; C for candidate,J for job and C for company',
  `deleted` char(1) NOT NULL COMMENT 'Show the status of the record;Y for delete and N for Not delete',
  `created` int(11) NOT NULL,
  `updated` int(11) NOT NULL COMMENT 'Modified Time of this record',
  `createdby` bigint(20) NOT NULL COMMENT 'Shows who is created this record',
  `updatedby` bigint(20) NOT NULL COMMENT 'shows who is modified this record\n',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=75 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `feedback`
--

DROP TABLE IF EXISTS `feedback`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `feedback` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(256) DEFAULT NULL,
  `email` varchar(256) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `member_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) unsigned DEFAULT NULL,
  `country` bigint(20) unsigned DEFAULT NULL,
  `state` bigint(20) unsigned DEFAULT NULL,
  `subject` bigint(20) unsigned DEFAULT NULL,
  `message` text,
  `created` int(11) NOT NULL,
  `createdby` bigint(20) DEFAULT NULL,
  `status` char(1) NOT NULL DEFAULT 'Y',
  PRIMARY KEY (`id`),
  KEY `FK_FEEDBACK_CND_COUNTRY` (`country`),
  KEY `FK_FEEDBACK_CND_STATE` (`state`),
  KEY `FK_FEEDBACK_USER_USERID` (`user_id`),
  CONSTRAINT `FK_FEEDBACK_CND_COUNTRY` FOREIGN KEY (`country`) REFERENCES `cnd` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_FEEDBACK_CND_STATE` FOREIGN KEY (`state`) REFERENCES `cnd` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_FEEDBACK_USER_USERID` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `job_alert`
--

DROP TABLE IF EXISTS `job_alert`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `job_alert` (
  `id` bigint(19) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id',
  `name` varchar(256) DEFAULT NULL COMMENT 'Job Alert Name',
  `frequency` bigint(19) unsigned DEFAULT NULL COMMENT 'How Frequently Job Alert need to sent;References cnd',
  `jobType` bigint(19) unsigned DEFAULT NULL COMMENT 'Employment Type:References cnd',
  `title` varchar(256) DEFAULT NULL COMMENT 'Title of the Job Alert',
  `functional_area` bigint(19) unsigned DEFAULT NULL COMMENT 'Classification/Functional/Sub Function:Lowest Child;References cnd',
  `experience` bigint(19) unsigned DEFAULT NULL COMMENT 'Experience ;References cnd',
  `salary` bigint(19) unsigned DEFAULT NULL COMMENT 'Salery : References cnd',
  `skillset` varchar(2048) DEFAULT NULL COMMENT 'SkillSet',
  `emailAlert` char(1) DEFAULT NULL COMMENT 'Would Like recieve Jobs on Email(Y or N)',
  `status` char(1) NOT NULL DEFAULT 'Y' COMMENT 'On(Y);Off(N)',
  `deleted` char(1) NOT NULL DEFAULT 'N' COMMENT '(Y) delete and (N) for Non Delete',
  `user_id` bigint(19) unsigned DEFAULT NULL COMMENT 'Candidate Id',
  `organisation` varchar(256) DEFAULT NULL COMMENT 'Organization\n',
  `created` int(11) NOT NULL COMMENT 'Created Time',
  `updated` int(11) NOT NULL COMMENT 'Updated Time',
  `createdby` bigint(20) NOT NULL COMMENT 'Created By Whom',
  `updatedby` bigint(20) NOT NULL COMMENT 'Updated By Whom',
  PRIMARY KEY (`id`),
  KEY `FK_JOBALERT_CND_FREQ` (`frequency`),
  KEY `FK_JOBALERT_CND_JOBTYPE` (`jobType`),
  KEY `FK_JOBALERT_CND_FUNCT` (`functional_area`),
  KEY `FK_JOBALERT_CND_EXP` (`experience`),
  KEY `FK_JOBALERT_USER_USERID` (`user_id`),
  KEY `FK_JOBALERT_CND_SAL` (`salary`),
  CONSTRAINT `FK_JOBALERT_CND_EXP` FOREIGN KEY (`experience`) REFERENCES `cnd` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_JOBALERT_CND_FREQ` FOREIGN KEY (`frequency`) REFERENCES `cnd` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_JOBALERT_CND_FUNCT` FOREIGN KEY (`functional_area`) REFERENCES `cnd` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_JOBALERT_CND_JOBTYPE` FOREIGN KEY (`jobType`) REFERENCES `cnd` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_JOBALERT_CND_SAL` FOREIGN KEY (`salary`) REFERENCES `cnd` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_JOBALERT_USER_USERID` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=217 DEFAULT CHARSET=utf8 COMMENT='Job Alerts For Candidate';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `jobfeeds`
--

DROP TABLE IF EXISTS `jobfeeds`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `jobfeeds` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `jobid` varchar(128) DEFAULT NULL,
  `source` varchar(128) DEFAULT NULL,
  `category` varchar(128) DEFAULT NULL,
  `title` varchar(512) DEFAULT NULL,
  `description` text,
  `link` varchar(2083) DEFAULT NULL,
  `pubdate` datetime DEFAULT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `scrores` text,
  `publisher` varchar(512) DEFAULT NULL,
  `location` varchar(512) DEFAULT NULL,
  `jobsid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `jobid_UNIQUE` (`jobid`)
) ENGINE=InnoDB AUTO_INCREMENT=465313 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `jobs`
--

DROP TABLE IF EXISTS `jobs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `jobs` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'job id of a job.',
  `user_id` bigint(20) unsigned NOT NULL COMMENT 'To which recruiter this job belongs; references users.id',
  `title` varchar(256) NOT NULL COMMENT 'Title of the job',
  `designation` varchar(128) DEFAULT NULL COMMENT 'Designation of the job',
  `salary_min` bigint(20) unsigned DEFAULT NULL COMMENT 'Compensation given by this job in dollers',
  `salary_max` bigint(20) unsigned DEFAULT NULL,
  `negotiable` char(1) DEFAULT NULL COMMENT 'Is the salary negotiable (Y for yes and N for no)',
  `qualification_required` bigint(20) unsigned DEFAULT NULL COMMENT 'Qualification for the job;references cnd.id',
  `qualification_preferred` bigint(20) unsigned DEFAULT NULL,
  `exp_required` bigint(20) unsigned DEFAULT NULL COMMENT 'Experience required for this job; references cnd.id',
  `exp_preferred` bigint(20) unsigned DEFAULT NULL,
  `skillsets` varchar(2048) DEFAULT NULL COMMENT 'Key skills required for this job',
  `functional_area` bigint(20) unsigned DEFAULT NULL COMMENT 'Functional area ; references cnd.id',
  `sub_fun_area` bigint(20) unsigned DEFAULT NULL COMMENT 'Sub functional area;references cnd.id',
  `job_type` bigint(20) unsigned DEFAULT NULL COMMENT 'Type of the job like contract, part time etc;references cnd.id',
  `contact` bigint(20) DEFAULT NULL COMMENT 'Contact reference of the job;',
  `location` bigint(20) unsigned DEFAULT NULL COMMENT 'Location of the job;references cnd.id',
  `brief_desc` varchar(2048) DEFAULT NULL COMMENT 'brief description of this job',
  `description` text COMMENT 'Detail description of this job',
  `status` char(1) NOT NULL DEFAULT 'A' COMMENT 'tells whether this job is active or not; A for active and I for inactive, M = moderation. Currently job is under moderation, H = Half saved, C=Closed',
  `source` varchar(45) DEFAULT NULL COMMENT 'Through Which Source the job is posted i.e. AAP for jobs via AAPC, IND for jobs via Indeed and HLK for jobs via site.',
  `last_renew_date` char(3) DEFAULT NULL COMMENT 'Last renew date of job',
  `zipcode` int(11) DEFAULT NULL COMMENT 'Zip Code',
  `deleted` char(1) NOT NULL DEFAULT 'N' COMMENT 'Shows status of the record; Y for delete and N for not delete',
  `createdby` bigint(20) NOT NULL COMMENT 'who is created this Record;references users.id',
  `updatedby` bigint(20) NOT NULL COMMENT 'Who is updated this Record;references users.id',
  `created` int(11) NOT NULL COMMENT 'created time ',
  `updated` int(11) NOT NULL COMMENT 'modified time',
  `company_id` bigint(20) unsigned DEFAULT NULL COMMENT 'Job Belongs to Which Company: Reference Company_Details.id',
  `company_confidential` char(1) DEFAULT NULL COMMENT 'WHether Company Info Keep confidential',
  PRIMARY KEY (`id`),
  KEY `FK_JOB_CND_FUNCTION` (`functional_area`),
  KEY `FK_JOB_CND_SUB_FUNCTION` (`sub_fun_area`),
  KEY `FK_JOB_CND_JOBTYPE` (`job_type`),
  KEY `FK_JOB_CND_LOC` (`location`),
  KEY `FK_JOB_USER_USERID` (`user_id`),
  KEY `FK_JOB_CND_EXP_REQ` (`exp_required`),
  KEY `FK_JOB_CND_EXP_PREF` (`exp_preferred`),
  KEY `FK_JOB_CND_QUALI_PREF` (`qualification_preferred`),
  KEY `FK_JOB_CND_QUALI_REQ` (`qualification_required`),
  KEY `FK_JOB_CND_SALARY_MIN` (`salary_min`),
  KEY `FK_JOB_CND_SALARY_MAX` (`salary_max`),
  KEY `FK_JOB_COMP_COMPID` (`company_id`),
  CONSTRAINT `FK_JOB_CND_EXP_PREF` FOREIGN KEY (`exp_preferred`) REFERENCES `cnd` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_JOB_CND_EXP_REQ` FOREIGN KEY (`exp_required`) REFERENCES `cnd` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_JOB_CND_FUNCTION` FOREIGN KEY (`functional_area`) REFERENCES `cnd` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_JOB_CND_JOBTYPE` FOREIGN KEY (`job_type`) REFERENCES `cnd` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_JOB_CND_LOC` FOREIGN KEY (`location`) REFERENCES `cnd` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_JOB_CND_QUALI_PREF` FOREIGN KEY (`qualification_preferred`) REFERENCES `cnd` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_JOB_CND_QUALI_REQ` FOREIGN KEY (`qualification_required`) REFERENCES `cnd` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_JOB_CND_SALARY_MAX` FOREIGN KEY (`salary_max`) REFERENCES `cnd` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_JOB_CND_SALARY_MIN` FOREIGN KEY (`salary_min`) REFERENCES `cnd` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_JOB_CND_SUB_FUNCTION` FOREIGN KEY (`sub_fun_area`) REFERENCES `cnd` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_JOB_COMP_COMPID` FOREIGN KEY (`company_id`) REFERENCES `company_details` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_JOB_USER_USERID` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=140639 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `jobs_metainfo`
--

DROP TABLE IF EXISTS `jobs_metainfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `jobs_metainfo` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'unique id',
  `user_id` bigint(20) unsigned DEFAULT NULL COMMENT 'User Id',
  `job_id` bigint(20) unsigned DEFAULT NULL COMMENT 'Job Id',
  `order_id` bigint(20) unsigned DEFAULT NULL COMMENT 'Order Id',
  `job_category` bigint(20) unsigned DEFAULT NULL COMMENT 'Job Type ; Featured or not.',
  `views` int(11) DEFAULT NULL COMMENT 'No of views',
  `application` int(11) DEFAULT NULL COMMENT 'no of application to this job',
  `field1` int(11) DEFAULT NULL,
  `field2` int(11) DEFAULT NULL,
  `deleted` char(1) NOT NULL COMMENT 'Show the status of the record;Y for delete and N for Not delete',
  `updated` int(11) NOT NULL COMMENT 'Modified Time of this record',
  `created` int(11) NOT NULL,
  `createdby` bigint(20) NOT NULL COMMENT 'Shows who is created this record',
  `updatedby` bigint(20) NOT NULL COMMENT 'shows who is modified this record\n',
  PRIMARY KEY (`id`),
  KEY `FK_METAJOB_USER_USERID_idx` (`user_id`),
  KEY `FK_METAJOB_JOB_JOBID_idx` (`job_id`),
  KEY `FK_METAJOB_ORDER_ORDERID_idx` (`order_id`),
  CONSTRAINT `FK_METAJOB_JOB_JOBID` FOREIGN KEY (`job_id`) REFERENCES `jobs` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_METAJOB_ORDER_ORDERID` FOREIGN KEY (`order_id`) REFERENCES `order` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_METAJOB_USER_USERID` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Id of the candidate profile linkage',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `recruiter_company`
--

DROP TABLE IF EXISTS `recruiter_company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `recruiter_company` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'linkage id between company  and recruiter id',
  `user_id` bigint(20) unsigned NOT NULL COMMENT 'recruiter id',
  `comp_id` bigint(20) unsigned NOT NULL COMMENT 'company id',
  `deleted` char(1) NOT NULL COMMENT 'Show the status of the record;Y for delete and N for Not delete',
  `created` int(11) NOT NULL,
  `updated` int(11) NOT NULL COMMENT 'Modified Time of this record',
  `createdby` bigint(20) NOT NULL COMMENT 'Shows who is created this record',
  `updatedby` bigint(20) NOT NULL COMMENT 'shows who is modified this record\n',
  PRIMARY KEY (`id`),
  KEY `FK_COMP_USER_USERID_idx` (`user_id`),
  KEY `FK_COMP_COMPDETAILS_COMPID_idx` (`comp_id`),
  CONSTRAINT `FK_COMP_COMPDETAILS_COMPID` FOREIGN KEY (`comp_id`) REFERENCES `company_details` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_COMP_USER_USERID` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `roles` (
  `id` int(5) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(256) NOT NULL COMMENT 'role name',
  `priority` int(5) DEFAULT NULL COMMENT 'priority in which role will be shown',
  `deleted` char(1) DEFAULT NULL COMMENT 'Show the status of the record;Y for delete and N for Not delete',
  `created` int(11) DEFAULT NULL,
  `updated` int(11) DEFAULT NULL COMMENT 'Modified Time of this record',
  `createdby` bigint(20) DEFAULT NULL COMMENT 'Shows who is created this record',
  `updatedby` bigint(20) DEFAULT NULL COMMENT 'shows who is modified this record\n',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `saved_jobs`
--

DROP TABLE IF EXISTS `saved_jobs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `saved_jobs` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id of the  saved jobs',
  `usr_id` bigint(20) unsigned DEFAULT NULL COMMENT 'the candidate who is saved this job ;referring to the id of the user table',
  `job_id` bigint(20) unsigned DEFAULT NULL COMMENT 'tells which job candidate is saved ;referring the id of the jobs table',
  `field1` varchar(64) DEFAULT NULL,
  `field2` varchar(64) DEFAULT NULL,
  `deleted` char(1) NOT NULL COMMENT 'Show the status of the record;Y for delete and N for Not delete',
  `created` int(11) NOT NULL,
  `updated` int(11) NOT NULL COMMENT 'Modified Time of this record',
  `createdby` bigint(20) NOT NULL COMMENT 'Shows who is created this record',
  `updatedby` bigint(20) NOT NULL COMMENT 'shows who is modified this record\n',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `FK_SAVEDJOBS_USER_CANDIDATEID_idx` (`usr_id`),
  KEY `FK_SAVEDJOBS_JOBS_JOBSID_idx` (`job_id`),
  CONSTRAINT `FK_SAVEDJOBS_JOBS_JOBSID` FOREIGN KEY (`job_id`) REFERENCES `jobs` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_SAVEDJOBS_USER_CANDIDATEID` FOREIGN KEY (`usr_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `search_history`
--

DROP TABLE IF EXISTS `search_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `search_history` (
  `id` bigint(19) unsigned NOT NULL AUTO_INCREMENT,
  `keyword` varchar(256) DEFAULT NULL,
  `search_string` varchar(2048) DEFAULT NULL,
  `created` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1125 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `subscription`
--

DROP TABLE IF EXISTS `subscription`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `subscription` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(64) DEFAULT NULL,
  `status` char(1) DEFAULT NULL COMMENT 'Subscribe S, Unsubscribe U',
  `deleted` char(1) NOT NULL COMMENT 'Show the status of the record;Y for delete and N for Not delete',
  `created` int(11) NOT NULL,
  `updated` int(11) NOT NULL COMMENT 'Modified Time of this record',
  `createdby` bigint(20) DEFAULT NULL COMMENT 'Shows who is created this record',
  `updatedby` bigint(20) DEFAULT NULL COMMENT 'shows who is modified this record\n',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id of a user per each registration\n',
  `email` varchar(128) NOT NULL COMMENT 'Email address of a user',
  `fname` varchar(128) DEFAULT NULL COMMENT 'First name of a user',
  `lname` varchar(128) DEFAULT NULL COMMENT 'Last name of a user',
  `mobile` varchar(16) DEFAULT NULL COMMENT 'Mobile Number of a user with country code. NUmber should be like +XXX-XXXXXXXXX',
  `user_type` char(1) NOT NULL COMMENT 'User Type. Recruiter (R) or Candidate (C)',
  `password` varchar(128) NOT NULL COMMENT 'Password of a user',
  `status` char(1) NOT NULL DEFAULT 'I' COMMENT 'Current status of a candidate : I for Inactive and A for active, default I',
  `last_login` int(11) DEFAULT NULL COMMENT 'Last login time of a user',
  `parent_usr_id` bigint(20) unsigned DEFAULT NULL,
  `deleted` char(1) NOT NULL COMMENT 'Show the status of the record;Y for delete and N for Not delete',
  `created` int(11) NOT NULL,
  `updated` int(11) NOT NULL COMMENT 'Modified Time of this record',
  `createdby` bigint(20) NOT NULL COMMENT 'Shows who is created this record',
  `updatedby` bigint(20) NOT NULL COMMENT 'shows who is modified this record\n',
  `source` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=1063 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `users_role`
--

DROP TABLE IF EXISTS `users_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) unsigned NOT NULL COMMENT 'user or subuser. All the roles will of a user will be in different rows.',
  `role_id` int(5) unsigned NOT NULL COMMENT 'Role of user of subuser. In case of subuser role will be given by parent user.',
  `deleted` char(1) NOT NULL COMMENT 'Show the status of the record;Y for delete and N for Not delete',
  `created` int(11) NOT NULL,
  `updated` int(11) NOT NULL COMMENT 'Modified Time of this record',
  `createdby` bigint(20) NOT NULL COMMENT 'Shows who is created this record',
  `updatedby` bigint(20) NOT NULL COMMENT 'shows who is modified this record\n',
  PRIMARY KEY (`id`),
  KEY `users_roles_users_id` (`user_id`),
  KEY `users_roles_role_id` (`role_id`),
  CONSTRAINT `users_roles_role_id` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `users_roles_users_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='Will store all the roles of the user. In case job search exp';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `users_role_log`
--

DROP TABLE IF EXISTS `users_role_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users_role_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT 'user or subuser. All the roles will of a user will be in different rows.',
  `role_id` int(5) unsigned NOT NULL COMMENT 'Role of user of subuser. In case of subuser role will be given by parent user.',
  `status_marked` char(1) DEFAULT NULL COMMENT 'What was the status marked on that role i.e if role was deleted/expired then put ''Y'', status ''N'' user was given permission on that day',
  `order_id` bigint(20) unsigned DEFAULT NULL COMMENT 'Against which order id this role was invoked',
  `deleted` char(1) NOT NULL COMMENT 'Show the status of the record;Y for delete and N for Not delete',
  `created` int(11) NOT NULL,
  `updated` int(11) NOT NULL COMMENT 'Modified Time of this record',
  `createdby` bigint(20) NOT NULL COMMENT 'Shows who is created this record',
  `updatedby` bigint(20) NOT NULL COMMENT 'shows who is modified this record\n',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Will store all the roles of the user. In case job search exp';
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-04-16 12:06:54
