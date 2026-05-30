package egovframework.lab.model;

import jakarta.validation.constraints.NotBlank;

public class Address {

	@NotBlank(message = "{required.member.address}")
	private String address;
	private String addressDetail;

	public String getAddress() {
    	return address;
    }

	public void setAddress(String address) {
    	this.address = address;
    }

	public String getAddressDetail() {
    	return addressDetail;
    }

	public void setAddressDetail(String addressDetail) {
    	this.addressDetail = addressDetail;
    }

}
