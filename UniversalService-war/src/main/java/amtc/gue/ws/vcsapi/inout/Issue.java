package amtc.gue.ws.vcsapi.inout;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * JAXB object for the Issue complex type
 * 
 * @author Thomas
 *
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "user", propOrder = {"issueDetail"})
public class Issue {
	// TODO Check what really needs to be sent
	@XmlElement(name = "issueDetail", required = true, nillable = false)
	private String issueDetail;

	// Getters and Setters
	public String getIssueDetail() {
		return issueDetail;
	}

	public void setIssueDetail(String issueDetail) {
		this.issueDetail = issueDetail;
	}
}
