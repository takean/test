package cn.itcast.activiti;


import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.junit.Test;

public class Test_Activiti {
	@Test
	public void createTableFromResource(){
//		ProcessEngineConfiguration configuration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml");
//		//创建流程引擎
//		ProcessEngine processEngine = configuration.buildProcessEngine();
		//链式编程
//		ProcessEngine processEngine =  ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml").buildProcessEngine();
//		System.out.println("processEngine:"+processEngine);
		
		ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResourceDefault();//自动加载activiti.cfg.xml文件
		ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();//获取引擎
		processEngine.getRepositoryService().createDeployment();//
	}
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	@Test
	public void deploymentProcessDefinitionByZIP(){
		InputStream in = this.getClass().getClassLoader().getResourceAsStream("diagrams/activiti.zip");//加载资源文件
		ZipInputStream zipInputStream = new ZipInputStream(in);
		Deployment deployment = processEngine.getRepositoryService()//获取与流程定义和部署对象的相关的Service
			.createDeployment()//创建部署对象
			.name("流程定义0202")//指定部署名称
			.addZipInputStream(zipInputStream)//使用zip的输入流完成
			.deploy();//部署
		System.out.println(deployment.getId()+"   "+deployment.getName());
	}
	@Test
	public void findDeploymentList(){
		List<Deployment> list = processEngine.getRepositoryService()//获取与流程定义和部署对象的相关的Service
			.createDeploymentQuery()//创建部署对象查询
			.orderByDeploymenTime().desc()//按照时间降序排列
			.list();
		if(list!=null && list.size()>0){
			for(Deployment deployment:list){
			System.out.println(deployment.getId()+""+deployment.getName());
			}
		}
	}
	/**查看流程定义*/
	@Test
	public void findProcessDefinitionList(){
		String processDefinitionKey = "myProcess";
		List<ProcessDefinition> list = processEngine.getRepositoryService()//获取与流程定义和部署对象的相关的Service
			.createProcessDefinitionQuery()//创建流程定义的查询对象
			/**查询条件*/
			.processDefinitionKey(processDefinitionKey)//按照流程定义的key查询
			/**排序*/
			.orderByProcessDefinitionVersion().asc()//安装版本的升序排列
			/**返回的结果集*/
	//.count();//结果集数量
	//.singleResult();//唯一结果
	//.listPage(firstResult, maxResults);//分页查询
			.list();//多个结果集
		if(list!=null && list.size()>0){
			for(ProcessDefinition pd:list){
				System.out.println("流程定义ID："+pd.getId());
				System.out.println("流程定义的key："+pd.getKey());
				System.out.println("流程定义的名称："+pd.getName());
				System.out.println("流程定义的版本："+pd.getVersion());
				System.out.println("部署对象ID："+pd.getDeploymentId());
				System.out.println("bpmn资源文件名称："+pd.getResourceName());
				System.out.println("png资源图片名称："+pd.getDiagramResourceName());		System.out.println("------------------------------------------------------");
			}
		}		
	}

}
