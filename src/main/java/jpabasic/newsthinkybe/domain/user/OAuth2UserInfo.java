package jpabasic.newsthinkybe.domain.user;

public interface OAuth2UserInfo {
    String getProviderId();
    String getProvider();
    String getProviderEmail();
    String getProviderName();
    String getProviderImage();
}
