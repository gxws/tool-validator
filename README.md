# tool-validator

mail list:朱伟亮 \<zhuwl120820@gxwsxx.com>

用于spring bean参数验证。<br>

版本变更说明
---
### 1.0.0
使用spring aop验证参数，抛出异常并返回结果。

功能点
---
### 1.输入参数验证
通过spring aop配置，验证输入参数，并根据抛出的异常返回带有提示的对象。<br>

使用方式
---
### 1. spring配置
创建spring-validator.xml，在execution中指定需要验证的aop。<br>

	<aop:config>
		<aop:aspect id="validate" ref="boParamValidateAspect">
			<aop:pointcut id="validatePointcut"
				expression="execution(public * com.gxws.toysearn.service.bo.impl..*.*(..))" />
			<aop:around method="around" pointcut-ref="validatePointcut" />
		</aop:aspect>
	</aop:config>
	<bean id="boParamValidateAspect" class="com.gxws.tool.validator.aspect.BoParamValidateAspect" />
	
### 2.验证参数配置
在需要验证的数据模型对象中，使用hibernate validator注解配置。<br>

	import IUserBo.FindRecordById;
	import IUserBo.FindUserById;
	import IUserBo.FindUserByStbId;
	
	public class UserDto extends BaseDto {
		@NotBlank(groups = { FindUserById.class, FindRecordById.class }, message = "id属性不能为空")
		private String id;
		@NotBlank(groups = { FindUserByStbId.class }, message = "stbId属性不能为空")
		private String stbId;
	}
	
在接口中定义需要验证的groups class，class名首字母大写，其他与方法名相同。<br>

	public interface IUserBo {
	
		public UserDto findUserById(UserDto u);
	
		public UserDto findUserByStbId(UserDto u);
	
		public UserDto findRecordById(UserDto u);
		
		public interface FindUserById {
		}
	
		public interface FindUserByStbId {
		}
	
		public interface FindRecordById {
		}
	}