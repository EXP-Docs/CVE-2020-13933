package com.exp.cve;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NameController {

    /**
     * 请求名为 name 的资源（触发身份认证）
     * @param name 资源名称
     * @return 资源
     */
    @GetMapping("/res/{name}")
    public String res(@PathVariable String name){
    	// TODO: any
        return "你发现了宝藏：" + name;
    }

    /**
     * 没有请求任何资源（不触发身份认证）
     * @return
     */
    @GetMapping("/res/")
    public String noRes(){
    	// TODO: any
        return "你没有请求任何资源";
    }
    
}