package jpo.sdw.depositor.depositors;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

public class SDWDepositor extends RestDepositor<String> {

   private static final Logger logger = LoggerFactory.getLogger(SDWDepositor.class);

   public SDWDepositor(RestTemplate restTemplate, URI destination) {
      super(restTemplate, destination);
   }

   @Override
   public void deposit(String message) {
      try {
         ResponseEntity<String> result = this.getRestTemplate().postForEntity(this.getDestination(), message,
               String.class);
         logger.debug("Response received. Status: {}, Body: {}", result.getStatusCode(), result.getBody());
      } catch (ResourceAccessException e) {
         logger.error("Failed to send message to destination", e);
      }
   }

}