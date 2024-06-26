package org.galaxio.gatling.amqp.javaapi.protocol;

import com.rabbitmq.client.ConnectionFactory;

public class RabbitMQConnectionFactoryBuilder{
    private org.galaxio.gatling.amqp.protocol.RabbitMQConnectionFactoryBuilder wrapped;

    public RabbitMQConnectionFactoryBuilder(org.galaxio.gatling.amqp.protocol.RabbitMQConnectionFactoryBuilder wrapped){
        this.wrapped = wrapped;
    }

    public RabbitMQConnectionFactoryBuilder username(String rabbitUsername){
        this.wrapped = wrapped.username(rabbitUsername);
        return this;
    }

    public RabbitMQConnectionFactoryBuilder password(String rabbitPassword){
        this.wrapped = wrapped.password(rabbitPassword);
        return this;
    }

    public RabbitMQConnectionFactoryBuilder port(Integer port){
        this.wrapped = wrapped.port(port);
        return this;
    }

    public RabbitMQConnectionFactoryBuilder vhost(String rabbitVHost){
        this.wrapped = wrapped.vhost(rabbitVHost);
        return this;
    }

    public ConnectionFactory build(){
        return wrapped.build();
    }
}
