package org.imageupload.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.imageupload.model.UserVO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author Georgia Papp
 */

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private static final Log LOG = LogFactory.getLog(CustomUserDetailsService.class);

    String repoUrlForGet = "http://192.168.1.127:{port}/users?page={page}&size={size}&username={username}";
    String repoUrlForPost = "http://192.168.1.127:8080/users";


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        LOG.info("Login attempted with username: " + username);

        UserVO user = null;
        try {
            user = getUser(username);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            return new org.springframework.security.core.userdetails.User(user.getUsername(),
                                                                          user.getPassword(),
                                                                          getGrantedAuthorities(user.getRole()));
        }catch (NullPointerException npe) {
            throw new NullPointerException(npe.getMessage());
        }
        catch (Exception e) {
           throw new RuntimeException(e);
        }

    }

    private List<GrantedAuthority> getGrantedAuthorities(String role) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

            authorities.add(new SimpleGrantedAuthority(role));

        return authorities;
    }



        public UserVO getUser(String username) throws MalformedURLException {

        //    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

       //     postUser("user", encoder.encode("user"), null, "ROLE_ADMIN");
            RestTemplate restTemplate = restTemplate();
            ResponseEntity<PagedResources<UserVO>> responseEntity = restTemplate.exchange(
                    repoUrlForGet, HttpMethod.GET, null,
                    new ParameterizedTypeReference<PagedResources<UserVO>>() {},
                    8080, 0, 100, username);
            PagedResources<UserVO> resources = responseEntity.getBody();
            List<UserVO> UserVOs = new ArrayList(resources.getContent());
            List<UserVO> indexedUserVOs = indexImages(UserVOs);

            return indexedUserVOs.get(0);
        }

        public void postUser(String username, String password, Date lastlogin, String role) {
            UserVO UserVO = new UserVO(username, password, lastlogin, role);
            RestTemplate restTemplate = restTemplate();
            restTemplate.postForLocation(repoUrlForPost, UserVO);
        }

        private RestTemplate restTemplate() {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            mapper.registerModule(new Jackson2HalModule());

            MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
            converter.setSupportedMediaTypes(MediaType.parseMediaTypes("application/hal+json"));
            converter.setObjectMapper(mapper);

            RestTemplate template = new RestTemplate(Collections.<HttpMessageConverter<?>> singletonList(converter));
            return template;
        }

        private List<UserVO> indexImages(List<UserVO> UserVOs) {
            List<UserVO> indexedImages = new ArrayList<UserVO>(UserVOs.size());
            int index = 0;

            for (UserVO UserVO: UserVOs) {
                UserVO.setId(index);
                indexedImages.add(UserVO);
                index++;
            }

            return indexedImages;
        }



}
