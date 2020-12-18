package com.zking.shiro01;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;

public class Demo {

    public static void main(String[] args) {
        //1.加载读取ini的shiro配置文件
        IniSecurityManagerFactory factory =
                new IniSecurityManagerFactory("classpath:shiro-permission.ini");

        //2.创建SecurityManager安全管理器
        SecurityManager securityManager = factory.getInstance();

        //3.将安全管理器委托给SecurityUtils
        SecurityUtils.setSecurityManager(securityManager);

        //4.创建Subject主体
        Subject subject = SecurityUtils.getSubject();

        //5.创建登录token令牌
        UsernamePasswordToken token = new UsernamePasswordToken(
                "admin",
                "123"
        );

        //异常：
        //authc   c代表认证器   z代表授权器
        //1.org.apache.shiro.authc.UnknownAccountException：账号不存在！
        //2.org.apache.shiro.authc.IncorrectCredentialsException：密码错误！(凭据错误)
        //3.org.apache.shiro.authz.UnauthorizedException：主体找不到角色！
        //org.apache.shiro.authz.UnauthorizedException：授权异常!

        //6.身份验证
        try {
            subject.login(token);
            System.out.println("用户认证成功！");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("用户认证失败！");
        }

        //8.角色认证
        try {
           /* if (subject.hasRole("role2")) {
                System.out.println("角色认证成功！");
            } else {
                System.out.println("角色认证失败！");
            }*/
            subject.checkRole("role1");
            System.out.println("角色认证成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //9.权限认证
        try {
           /* if (subject.isPermitted("system:user:delete")) {
                System.out.println("权限验证成功！");
            } else {
                System.out.println("权限验证失败！");
            }*/
            subject.checkPermission("system:user:delete");
            System.out.println("权限验证成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }


        //7.安全退出
        subject.logout();

    }
}
