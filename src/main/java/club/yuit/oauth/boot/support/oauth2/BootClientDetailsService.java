package club.yuit.oauth.boot.support.oauth2;

import club.yuit.oauth.boot.entity.Client;
import club.yuit.oauth.boot.service.IClientService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Component;

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
        client.setIsAutoApprove(true);
        client.setScope("all");
        client.setAuthorizedGrantTypes("authorization_code");
        client.setAuthorities("ROLE_USER");

        if(client==null){
            throw new ClientRegistrationException("客户端不存在");
        }
        BootClientDetails details=new BootClientDetails(client);

        return details;
    }

}
