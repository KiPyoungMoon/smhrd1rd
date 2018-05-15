package CinemaChain;

public class MemberDTO {
	private String eMail;
	private String birth;
	private String name;

	public MemberDTO(String eMail, String birth, String name) {
		this.eMail = eMail;
		this.birth = birth;
		this.name = name;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String geteMail() {
		return eMail;
	}

	public String getBirth() {
		return birth;
	}

	public String getName() {
		return name;
	}

}
