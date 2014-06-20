package com.hulk.jobs.model;

import org.codehaus.jackson.annotate.JsonProperty;

public class AAPCJob {

	@JsonProperty("$id")
	private String id;

	@JsonProperty("PartTime")
	private Boolean parttime;

	@JsonProperty("CompanyName")
	private String companyname;

	@JsonProperty("Hourly")
	private Boolean hourly;

	@JsonProperty("ZipCode")
	private String zipcode;

	@JsonProperty("JobDescription")
	private String jobdescription;

	@JsonProperty("PostedBy")
	private String postedby;

	@JsonProperty("MinPay")
	private Integer minpay;

	@JsonProperty("City")
	private String city;

	@JsonProperty("VoucherQuantity")
	private Integer voucherquantity;

	@JsonProperty("Contract")
	private Boolean contract;

	@JsonProperty("PostedByCustomerId")
	private Integer postedbycustomerid;

	@JsonProperty("MaxPay")
	private Integer maxpay;

	@JsonProperty("PostedDate")
	private String posteddate;

	@JsonProperty("Intern")
	private Boolean intern;

	@JsonProperty("JobTitle")
	private String jobtitle;

	@JsonProperty("Temporary")
	private Object temporary;

	@JsonProperty("StreetAddress")
	private String streetaddress;

	@JsonProperty("VoucherExpiration")
	private String voucherexpiration;

	@JsonProperty("ContactText")
	private String contacttext;

	@JsonProperty("State")
	private String state;

	@JsonProperty("FullTime")
	private Boolean fulltime;

	@JsonProperty("$ref")
	private String ref;
	
	
	
	public void setParttime(Boolean parttime) {
		this.parttime = parttime;
	}

	public Boolean getParttime() {
		return parttime;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getCompanyname() {
		return companyname;
	}

	public void setHourly(Boolean hourly) {
		this.hourly = hourly;
	}

	public Boolean getHourly() {
		return hourly;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setJobdescription(String jobdescription) {
		this.jobdescription = jobdescription;
	}

	public String getJobdescription() {
		return jobdescription;
	}

	public void setPostedby(String postedby) {
		this.postedby = postedby;
	}

	public String getPostedby() {
		return postedby;
	}

	public void setMinpay(Integer minpay) {
		this.minpay = minpay;
	}

	public Integer getMinpay() {
		return minpay;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCity() {
		return city;
	}

	public void setVoucherquantity(Integer voucherquantity) {
		this.voucherquantity = voucherquantity;
	}

	public Integer getVoucherquantity() {
		return voucherquantity;
	}

	public void setContract(Boolean contract) {
		this.contract = contract;
	}

	public Boolean getContract() {
		return contract;
	}

	public void setPostedbycustomerid(Integer postedbycustomerid) {
		this.postedbycustomerid = postedbycustomerid;
	}

	public Integer getPostedbycustomerid() {
		return postedbycustomerid;
	}

	public void setMaxpay(Integer maxpay) {
		this.maxpay = maxpay;
	}

	public Integer getMaxpay() {
		return maxpay;
	}

	public void setPosteddate(String posteddate) {
		this.posteddate = posteddate;
	}

	public String getPosteddate() {
		return posteddate;
	}

	public void setIntern(Boolean intern) {
		this.intern = intern;
	}

	public Boolean getIntern() {
		return intern;
	}

	public void setJobtitle(String jobtitle) {
		this.jobtitle = jobtitle;
	}

	public String getJobtitle() {
		return jobtitle;
	}

	public void setTemporary(Object temporary) {
		this.temporary = temporary;
	}

	public Object getTemporary() {
		return temporary;
	}

	public void setStreetaddress(String streetaddress) {
		this.streetaddress = streetaddress;
	}

	public String getStreetaddress() {
		return streetaddress;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setVoucherexpiration(String voucherexpiration) {
		this.voucherexpiration = voucherexpiration;
	}

	public String getVoucherexpiration() {
		return voucherexpiration;
	}

	public void setContacttext(String contacttext) {
		this.contacttext = contacttext;
	}

	public String getContacttext() {
		return contacttext;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getState() {
		return state;
	}

	public void setFulltime(Boolean fulltime) {
		this.fulltime = fulltime;
	}

	public Boolean getFulltime() {
		return fulltime;
	}
	
	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	@Override
	public String toString() {
		return "AAPCJob [id=" + id + ", parttime=" + parttime
				+ ", companyname=" + companyname + ", hourly=" + hourly
				+ ", zipcode=" + zipcode + ", jobdescription=" + jobdescription
				+ ", postedby=" + postedby + ", minpay=" + minpay + ", city="
				+ city + ", voucherquantity=" + voucherquantity + ", contract="
				+ contract + ", postedbycustomerid=" + postedbycustomerid
				+ ", maxpay=" + maxpay + ", posteddate=" + posteddate
				+ ", intern=" + intern + ", jobtitle=" + jobtitle
				+ ", temporary=" + temporary + ", streetaddress="
				+ streetaddress + ", voucherexpiration=" + voucherexpiration
				+ ", contacttext=" + contacttext + ", state=" + state
				+ ", fulltime=" + fulltime + "]";
	}
}