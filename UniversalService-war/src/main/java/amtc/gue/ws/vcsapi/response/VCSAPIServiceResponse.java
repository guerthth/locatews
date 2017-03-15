package amtc.gue.ws.vcsapi.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import amtc.gue.ws.base.response.ServiceResponse;
import amtc.gue.ws.vcsapi.inout.VCSIssue;

/**
 * JAXB object for the VCSAPIServiceResponse complex type
 * 
 * @author Thomas
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class VCSAPIServiceResponse extends ServiceResponse {
	private VCSIssue issue;

	// Getters and Setters
	public VCSIssue getIssue() {
		return issue;
	}

	public void setIssue(VCSIssue issue) {
		this.issue = issue;
	}
}
