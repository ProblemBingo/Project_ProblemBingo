package Data;

import java.io.Serializable;
import java.util.HashMap;

public class Data implements Serializable {
	String protocol;
	String category;
	String answer;
	String problem;
	int qNum;
	int item;
	boolean check;
	DTO dto;
	String name;
	int RSP;

	/* protocol data */
	public Data(String protocol) {
		this.protocol = protocol;
	}
	
	/* protocol and checking data */
	public Data(String protocol, boolean check) {
		this.protocol = protocol;
		this.check = check;
	}
	
	/* protocol, quiz number, problem, category data */
	public Data(String protocol, int qNum, String problem, String category) {
		this.protocol = protocol;
		this.qNum = qNum;
		this.problem = problem;
		this.category = category;
	}
	
	/* protocol, category, quiz number data */
	public Data(String protocol, String category, int qNum) {
		this.protocol = protocol;
		this.category = category;
		this.qNum = qNum;
	}
	
	/* protocol, name data */
	public Data(String protocol, String name) {
		this.protocol = protocol;
		this.name = name;
	}
	
	/* protocol, name, quiz number, category data */
	public Data(String protocol, String name, int qNum, String category) {
		this.protocol = protocol;
		this.name = name;
		this.qNum = qNum;
	}
	
	/* protocol, answer, category, quiz number, name data */
	public Data(String protocol, String answer, String category, int qNum, String name) {
		this.protocol = protocol;
		this.answer = answer;
		this.category = category;
		this.qNum = qNum;
		this.name = name;
	}
	
	/* protocol, DTO data */
	public Data(String protocol, DTO dto) {
		this.protocol = protocol;
		this.dto = dto;
	}
	
	/* protocol, RSP status data */
	public Data(String protocol, int RSP) {
		this.protocol = protocol;
		this.RSP = RSP;
	}
	
	/* Getting protocol */
	public String getProtocol() {
		return protocol;
	}
	
	/* Getting category */
	public String getCategory() {
		return category;
	}
	
	/* protocol, item status, category, quiz number data */
	public Data(String protocol, int item, String category, int qNum) {
		this.protocol = protocol;
		this.item = item;
		this.category = category;
		this.qNum = qNum;
	}
	
	/* Getting problem */
	public String getProblem() {
		return problem;
	}
	
	/* Getting answer */
	public String getAnswer() {
		return answer;
	}
	
	/* Getting quiz number */
	public int getNum() {
		return qNum;
	}
	
	/* Getting checking */
	public boolean getCheck() {
		return check;
	}
	
	/* Getting name */
	public String getName() {
		return name;
	}
	
	/* Getting DTO */
	public DTO getDTO() {
		return dto;
	}

	/* Getting RPS status */
	public int getRSP() {
		return RSP;
	}
	
	/* Getting item status */
	public int getItem() {
		return item;
	}
}
