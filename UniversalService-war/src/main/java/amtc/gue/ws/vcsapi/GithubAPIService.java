package amtc.gue.ws.vcsapi;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import amtc.gue.ws.base.delegate.output.IDelegatorOutput;
import amtc.gue.ws.base.response.ServiceResponse;
import amtc.gue.ws.base.util.BaseServiceEntityMapper;
import amtc.gue.ws.base.util.DelegatorTypeEnum;
import amtc.gue.ws.base.util.SpringContext;
import amtc.gue.ws.vcsapi.delegate.AbstractAPIDelegator;
import amtc.gue.ws.vcsapi.delegate.GithubAPIDelegator;
import amtc.gue.ws.vcsapi.inout.VCSIssue;

@Path("/gitapi")
@Produces(MediaType.APPLICATION_JSON)
public class GithubAPIService {
	private static final Logger log = Logger.getLogger(GithubAPIService.class.getName());
	private AbstractAPIDelegator githubAPIDelegator;

	public GithubAPIService() {
		githubAPIDelegator = (GithubAPIDelegator) SpringContext.context.getBean("githubAPIDelegator");
	}

	public GithubAPIService(AbstractAPIDelegator githubAPIDelegator) {
		this.githubAPIDelegator = githubAPIDelegator;
	}

	@PermitAll
	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ServiceResponse addIssueToProject(VCSIssue issue) {
		log.log(Level.INFO, "'addIssueToProject' method called.");
		githubAPIDelegator.buildAndInitializeDelegator(DelegatorTypeEnum.ADD, issue);
		IDelegatorOutput dOutput = githubAPIDelegator.delegate();
		return BaseServiceEntityMapper.mapBdOutputToServiceResponse(dOutput);
	}
}
