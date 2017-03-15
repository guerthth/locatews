package amtc.gue.ws.vcsapi.inout;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * JAXB object for the VCSIssues complex type
 * 
 * @author Thomas
 *
 */
@XmlAccessorType(XmlAccessType.NONE)
public class VCSIssues {
	@XmlElement(name = "books")
	List<VCSIssue> issues;

	/**
	 * Setter for the VCSIssue list
	 * 
	 * @return VCSIssue list
	 */
	public List<VCSIssue> getIssues() {
		if(issues == null){
			issues = new ArrayList<>();
		}
		return issues;
	}

	/**
	 * Setter for VCSIssue list
	 * 
	 * @param issues
	 *            List of VCSList
	 */
	public void setIssues(List<VCSIssue> issues) {
		this.issues = issues;
	}

}
