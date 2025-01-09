package com.core.aspect;


import com.core.domain.IClubRepository;
import com.core.domain.cq.ClubUpdateCommand;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class ClubAdminAuthAspect {
    private final IClubRepository clubRepository;

    public ClubAdminAuthAspect(IClubRepository clubRepository) {
        this.clubRepository = clubRepository;
    }

    @Pointcut("@annotation(com.core.attribute.AuthClubAdmin)")
    public void attributeMethod() {

    }

    @Before("attributeMethod()")
    public void before(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        var paraList = Arrays.stream(methodSignature.getMethod().getParameters()).toList();
        var authPara = paraList.stream().filter(parameter ->
                parameter.getType().equals(ClubUpdateCommand.class)
                ).findFirst();

        if (authPara.isPresent()) {
            ClubUpdateCommand command =(ClubUpdateCommand) joinPoint.getArgs()[paraList.indexOf(authPara.get())];
            if(!clubRepository.checkClubAdminCondition(command.getUserId(),command.getClubId())){
                throw new IllegalArgumentException("club admin operation command argument error");
            }
        }else{
            throw new IllegalArgumentException("club admin operation command argument error");
        }

    }


}
