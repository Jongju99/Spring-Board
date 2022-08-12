package com.board.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
//스프링 컨테이너에 빈으로 등록하기 위한 애너테이션.
//@Bean은 개발자가 제어할 수 없는 외부 라이브러리를 빈으로 등록할 때 사용하고, 
//@Component는 개발자가 직접 정의한 클래스를 빈으로 등록할 때 사용.
@Aspect
//AOP기능을 하는 클래스의 클래스 레벨에 지정하는 애너테이션.
public class LoggerAspect {
	//AOP(Aspect Oriented Programming) 관점 지향 프로그래밍
	// 자바와 같은 객체지향프로그래밍(OOP)를 더욱 OOP답게 사용할 수 있도록 도와주는 역할.
	// 모듈화
	
	//AOP 용어
	//관점(Aspect): 공통적으로 적용될 기능. 
	// 	부가적인 기능을 정의한 코드인 어드바이스와 어드바이스를 어느 곳에 적용할 지 결정하는 포인트컷의 조합.
	//어드바이스(Advice): 실제로 부가적인 기능을 구현한 객체를 의미.
	//조인 포인트(Join point): 어드바이스를 적용할 위치를 의미.
	//포인트컷(Pointcut): 어드바이스를 적용할 조인 포인트를 선별하는 과정이나, 그 기능을 정의한 모듈.
	//타겟(Target): 실제로 비즈니스 로직을 수행하는 객체를 의미. 즉 어드바이스를 적용할 대상.
	//프록시(Proxy): 어드바이스가 적용되었을 때 생성되는 객체를 의미.
	//인트로덕션(Introduction): 타겟에는 없는 새로운 메서드나 인스턴스 변수를 추가하는 기능.
	//위빙(Weaving): 포인트컷에 의해서 결정된 타겟의 조인 포인트에 어드바이스를 적용하는 것을 의미.
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Around("execution(* com.board..controller.*Controller.*(..)) or execution(* com.board..service.*Impl.*(..)) or execution(* com.board..mapper.*Mapper.*(..))")
	//@Around: 어드바이스의 종류 중 한가지. 메서드의 호출을 제어할 수 있음.
	//execution으로 시작하는 구문: 포인트컷을 지정하는 문법.
	public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {

		String type = "";
		String name = joinPoint.getSignature().getDeclaringTypeName();

		if (name.contains("Controller") == true) {
			type = "Controller ===> ";

		} else if (name.contains("Service") == true) {
			type = "ServiceImpl ===> ";

		} else if (name.contains("Mapper") == true) {
			type = "Mapper ===> ";
		}

		logger.debug(type + name + "." + joinPoint.getSignature().getName() + "()");
		return joinPoint.proceed();
	}

}