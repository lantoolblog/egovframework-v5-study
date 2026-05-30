package egovframework.lab.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public class MemberInfo {

	@NotBlank(message = "{required.member.id}")
	@Pattern(regexp = "^[a-zA-Z0-9]+$", message = "{invalid.message}")
	private String id;

	@NotBlank(message = "{required.member.name}")
	private String name;

	@Valid
	private Address address;

	@NotEmpty(message = "{required.member.favorites}")
	private String[] favorites;

	@NotBlank(message = "{required.member.jobCode}")
	private String jobCode;

	@NotBlank(message = "{required.member.tool}")
	private String tool;
	private String etc;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String[] getFavorites() {
		return favorites;
	}

	public void setFavorites(String[] favorites) {
		this.favorites = favorites;
	}

	public String getJobCode() {
		return jobCode;
	}

	public void setJobCode(String jobCode) {
		this.jobCode = jobCode;
	}

	public String getTool() {
		return tool;
	}

	public void setTool(String tool) {
		this.tool = tool;
	}

	public String getEtc() {
		return etc;
	}

	public void setEtc(String etc) {
		this.etc = etc;
	}

}
