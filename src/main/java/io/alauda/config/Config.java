package io.alauda.config;

import lombok.Data;

public @Data
class Config {
    // 是否用户指定的配置文件, true 为用户指定.
    private boolean custom ;
    private Source source;
    private Sink sink;

//    static String LocalCSVPATH = "src/main/resources/Water_demo_dataset.csv";
//
//    private String source; // local or remote
//
//    private String localCSVPath; // local 模式下 外部cvs文件的绝对路径
//    private int localRate;   // 每秒数据个数
//
//    public String getSource() {
//        return source;
//    }
//
//    public void setSource(String source) {
//        this.source = source;
//    }
//
//    public String getLocalCSVPath() {
//        return localCSVPath;
//    }
//
//    public void setLocalCSVPath(String localCSVPath) {
//        this.localCSVPath = localCSVPath;
//    }
//
//    public int getLocalRate() {
//        return localRate;
//    }
//
//    public void setLocalRate(int localRate) {
//        this.localRate = localRate;
//    }
//
//
//    public Config withDefault() {
//        if ("local".equalsIgnoreCase(this.getSource())) {
//            if (null == this.getLocalCSVPath() || "".equals(this.getLocalCSVPath())) {
//                this.setLocalCSVPath(LocalCSVPATH);
//            }
//        }
//
//        return this;
//    }
}
