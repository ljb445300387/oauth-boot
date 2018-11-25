package club.yuit.oauth.boot.support.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Component;

import club.yuit.oauth.boot.entity.Client;
import club.yuit.oauth.boot.service.IClientService;

/**
 * @author yuit
 * @create time 2018/10/16  9:22
 * @description
 * @modify by
 * @modify time
 **/
@Component
public final class BootClientDetailsService implements ClientDetailsService {

	@Autowired
	private IClientService clientService;

	@Override
	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {

		Client client = this.clientService.findClientByClientId(clientId);

		if (client == null) {
			throw new ClientRegistrationException("客户端不存在");
		}
		client.setIsAutoApprove(true);
		client.setAuthorities("ROLE_USER");
		return new BootClientDetails(client);
	}

}
