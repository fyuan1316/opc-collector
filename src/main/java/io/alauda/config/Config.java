package io.alauda.config;

import lombok.Data;

public @Data
class Config {
    // 标识这个配置文件是不是用户通过参数方式指定，当值为true时为用户指定，false时是使用jar包内置的参数文件.
    private boolean custom;
    private Source source;
    private Sink sink;
    private Run run;
}
