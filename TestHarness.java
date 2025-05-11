package uk.momentousmoments.utilities;


import org.apache.activemq.artemis.api.core.TransportConfiguration;
import org.apache.activemq.artemis.api.jms.ActiveMQJMSClient;
import org.apache.activemq.artemis.api.jms.JMSFactoryType;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.apache.activemq.artemis.jms.client.ActiveMQSession;
import org.apache.activemq.artemis.core.remoting.impl.netty.NettyConnectorFactory;

// this works with javax version which is artemis-jakarta-client-all-2.40.0.jar
//import javax.jms.*;
// Use jakarta version
import jakarta.jms.*;


// To run:
// java -jar testharness.jar
public class TestHarness {
    public static void main(String[] args) {


        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        Session jmsSession = null;
        try {
            Connection connection = connectionFactory.createConnection();
            jmsSession = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer producer = jmsSession.createProducer(jmsSession.createQueue("Queue123"));
            connection.start();
            TextMessage message = jmsSession.createTextMessage("Hello, Artemis!");
            producer.send(message);
            System.out.println("Message sent: " + message.getText());


            MessageConsumer consumer = jmsSession.createConsumer(jmsSession.createQueue("Queue123"));
            TextMessage receivedMessage = (TextMessage) consumer.receive();
            System.out.println("Got order: " + receivedMessage.getText());
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }

    }
/*
// Example form data being sent to localhost:8080/api/v1/list endpoint
    // curl -H "Content-Type: application/x-www-form-urlencoded" -X POST http://localhost:8080/api/v1/call
    @Path("/call")
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public Response call(
            ) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        Map<String, String> formData = new HashMap<>();
        formData.put("key", "k1");
        formData.put("value", "v1");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/v1/list"))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(getFormDataAsString(formData)))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return Response.ok().build();
    }
 */

}