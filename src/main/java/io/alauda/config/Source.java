package io.alauda.config;


import lombok.Data;


public @Data
class Source {
    String from;
    Local local;
    Remote remote;
}
