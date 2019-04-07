package com.efangtec.common.mq.consumer.spring;

import com.efangtec.common.mq.consumer.annotation.Consumer;
import com.efangtec.common.mq.consumer.annotation.Listener;
import com.efangtec.common.mq.consumer.core.Event;
import com.efangtec.common.mq.consumer.core.MessageMethodHandle;
import com.efangtec.common.mq.consumer.core.RepeatMessageHandle;
import com.google.common.collect.Lists;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQSession;
import org.apache.activemq.RedeliveryPolicy;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.Executor;


public class MessageConsumerBeanPostProcessor implements BeanPostProcessor, Ordered, BeanFactoryAware, SmartInitializingSingleton {

    private Object primitiveBean;
    //bean����
    private BeanFactory beanFactory;
    //ActiveMQ���ӹ���
    private ActiveMQConnectionFactory activeMQConnectionFactory;
    //�̳߳�
    private Executor executor;
    //��Ϣ�ݵȴ���
    private RepeatMessageHandle repeatMessageHandle;
    //�����߼���
    private List<Object> consumerBeans = Lists.newArrayList();

    public MessageConsumerBeanPostProcessor() {
    }
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        this.primitiveBean = bean;
        return bean;
    }
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Consumer consumer = null;
        Object consumerBean = null;
        if (AopUtils.isAopProxy(bean)) {
            consumer = primitiveBean.getClass().getAnnotation(Consumer.class);
            consumerBean = primitiveBean;
        }else{
            consumer =bean.getClass().getAnnotation(Consumer.class);
            consumerBean = bean;
        }
        if (consumer != null) {
            consumerBeans.add(consumerBean);
        }
        return bean;
    }

    /**
     * ע����Ϣ���м�����
     * @param bean
     */
    private void register(Object bean) throws Exception {
        Class<?> clazz = bean.getClass();
        //��ȡ@Listenerע��ķ���
        for (Method method : clazz.getDeclaredMethods()) {
            Class<?>[] types = method.getParameterTypes();
            //����������������Ϊ1�����Ҳ���������ΪEvent������
            if (types.length != 1 || !Event.class.isAssignableFrom(types[0])) {
                continue;
            }
            //��ȡ��Ϣ������
            Listener listener = method.getAnnotation(Listener.class);
            //��������
            Connection connection = activeMQConnectionFactory.createConnection();
            RedeliveryPolicy policy = ((ActiveMQConnection) connection).getRedeliveryPolicy();
            //�������Բ���
            policy.setInitialRedeliveryDelay(1000);
            policy.setBackOffMultiplier(2);
            policy.setUseExponentialBackOff(true);
            policy.setMaximumRedeliveries(2);
            //��������
            connection.start();
            //�����Ự
            Session session = connection.createSession(listener.transaction(), ActiveMQSession.AUTO_ACKNOWLEDGE);
            //��������
            Destination queue = session.createQueue(listener.topic());
            //����������
            MessageConsumer consumer = session.createConsumer(queue);
            //������Ϣ������
            MessageMethodHandle messageMethodHandle = new MessageMethodHandle()
                    .setBean(bean)
                    .setDestination(listener.topic())
                    .setExecutor(executor)
                    .setMethod(method)
                    .setN2(listener.n2())
                    .setSession(session)
                    .setTransaction(listener.transaction())
                    .setRepeatMessageHandle(repeatMessageHandle);
            //������Ϣ������
            consumer.setMessageListener(messageMethodHandle);
        }
    }

    /**
     * ����bean������ɺ�����Ϣ������
     */
    public void afterSingletonsInstantiated() {
        activeMQConnectionFactory = (ActiveMQConnectionFactory) beanFactory.getBean("activeMQConnectionFactory");
        executor = (Executor) beanFactory.getBean("executor");
        repeatMessageHandle = (RepeatMessageHandle) beanFactory.getBean("repeatMessageHandle");
        try {
            for (Object consumerBean : consumerBeans) {
                //���������߼�����
                register(consumerBean);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int getOrder() {
        return LOWEST_PRECEDENCE;
    }

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
