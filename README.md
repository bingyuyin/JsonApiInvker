# json-api-invoker

## Backgroud

Many services have expose some specific JSON messages as the API protocol.

1. One service/client send a JSON message to another service for RPC.

2. The other serice receives a JSON message to invoke some specific logic inside the service.


## Motivation

To avoid parse the JSON message each time manually:

1. Create a framework that can parse the JSON message according the some specific model.

2. The framework can parse the JSON message, and invoke a specific logic inside the service.

3. Implement something just the Spring annotation framework(@Controller, @Component, etc).

## Design

1. Message model definition:

  In common designs, the message contain two parts: Header and Body.
  
  --The Header contains the information like 'requestId', 'sourceId', 'originalId' which are used to identify the caller, the source and sender.
  
  --The Body contains the message data, that the service will used to invoke a specific logic.
  
  So, the lib provided the annotations below  for model definition:
  
  @APIComponentDefinition, @APIDefinition, @APIParametersDefinition, @APIParameterDefinition, @APIHeaderDefinition
  
  A sample message model might be:
  
  
      public class MessageModel {

        @APIHeaderDefinition

        private String requestId;      

        private MessageBody body;

        // getters for each field

        private class MessageBody {

          @APIComponentDefinition

          private String command;

          private MessageContent content;

          // getters for each field

          private class MessageContent {

            @APIParametersDefinition

            private Map<String, Object> paramterMap;

            @APIParametersDefinition

            private ParametersSampeModel paramterModel;

            @APIParameterDefinition

            private SingleParameterModel singleParameterModel;

            // getters for the fields

          }
        }  
      }
  

2. Invoke strategy:

  1) The framework will parse the message model, and find the @APIComponentDefintion, @APIDefinition, @APIParametersDefintion, etc.
  
  2) Then it will scan a basepackage to find the compoents with the annotation: @APIComponent,
  
  3) It will invoke the most match @API, inside each @APICompoent.
  
3. Work flow

   ![Alt text](https://github.com/bingyuyin/json-api-invoker/assets/work-flow.png "Work Flow")
  
## Demo

Check a details demo under the package **.Sample
