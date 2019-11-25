package com.ljt.study.agent.attach;

import com.ljt.study.agent.AgentTest;
import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

import java.util.List;

/**
 * 对正在运行的应用agent
 *
 * @author LiJingTang
 * @date 2019-11-21 10:59
 */
public class LoadAgent {

    private static final String AGENT_PATH = "D:/Workspace/IDEA/study/target/artifacts/agent/agent-agentmain.jar";

    public static void main(String[] args) throws Exception {
        List<VirtualMachineDescriptor> vmList = VirtualMachine.list();

        for (VirtualMachineDescriptor vm : vmList) {
            // java -jar agent-test.jar方式启动
//            String appName = "agent-test.jar";
            // IDE方式启动
            String appName = AgentTest.class.getSimpleName();
            // 选择要代理的应用
            if (vm.displayName().endsWith(appName)) {
                VirtualMachine v = VirtualMachine.attach(vm.id());
                v.loadAgent(AGENT_PATH, "agentmain-args");
                v.detach();
                System.out.println(appName + " 加载 " + AGENT_PATH);
            }
        }
    }

}
