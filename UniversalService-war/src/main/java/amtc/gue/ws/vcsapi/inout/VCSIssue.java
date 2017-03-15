package amtc.gue.ws.vcsapi.inout;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * JAXB object for the VCSIssue complex type
 * 
 * @author Thomas
 *
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "user", propOrder = { "issueId", "issueTitle", "issueDetail", "issueState" })
public class VCSIssue {
	@XmlElement(name = "issueId", required = true, nillable = false)
	private int issueId;
	@XmlElement(name = "issueTitle", required = true, nillable = false)
	private String issueTitle;
	@XmlElement(name = "issueDetail", required = true, nillable = false)
	private String issueDetail;
	@XmlElement(name = "issueState", required = true, nillable = false)
	private String issueState;

	// Getters and Setters
	public int getIssueId() {
		return issueId;
	}

	public void setIssueId(int issueId) {
		this.issueId = issueId;
	}

	public String getIssueTitle() {
		return issueTitle;
	}

	public void setIssueTitle(String issueTitle) {
		this.issueTitle = issueTitle;
	}

	public String getIssueDetail() {
		return issueDetail;
	}

	public void setIssueDetail(String issueDetail) {
		this.issueDetail = issueDetail;
	}

	public String getIssueState() {
		return issueState;
	}

	public void setIssueState(String issueState) {
		this.issueState = issueState;
	}
}
