package kr.co.cofile.sbapivite.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RootConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true) // private 필드도 매핑 가능하도록 설정
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE) // private 필드에 대해 접근 허용
                .setMatchingStrategy(MatchingStrategies.LOOSE); // 느슨한 매칭으로 필드명이 완전히 일치하지 않아도 매칭 가능
        return modelMapper;
    }
}
