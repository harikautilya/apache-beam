# Setup

## Intial Setup
1. Create a google account
2. Enabled Cloud Resource Manager API api
3. Update the project id in pulumi.dev.yaml  

## Runtime setup
This has to be perfomed every time

1. `gcloud auth login` for login into gcloud
2. `gcloud auth application-default login` for using ADC
3. `pulumi up` to setup the infra

## Pulumi project setup
This project has only one env , ideally we would have dev and prod (sometimes staging/pre-prod) but as the project is never expecteded to hit an actual market only dev will be maintained 

### Stack 
1. Display available stacks
```
pulumi stack ls
```
2. Select the dev stack
```
pulumn stack select dev
```

### Project Strcuture
Pulumi holds the concept of resource and components, where components is group of resource provisioned together which acts a single unit. This project will take advantage of such grouping to ensure that each application designed as part of this project can effectively take control of their resources

Here is an example of component resource 
```python
class MyComponent(pulumi.ComponentResource):
    def __init__(self, name, my_component_args, opts = None):
        super().__init__('pkg:index:MyComponent', name, None, opts)
        # Declaring dependecy of parent
        bucket = s3.Bucket(f"{name}-bucket", opts = pulumi.ResourceOptions(parent=self))
        # Declaring output of the component
        # As a standard partice call it in every compoent event when there is no output
        self.register_outputs({
          "Bucket name" : bucket.bucketDomainName
        })
```

All the configuration of the project such as region should be passed with the use of providers

```python
component = SampleComponent("...",  ResourceOptions(providers={
  "gcp-region" : south-asia1
}))
```

### Resources

#### GCP PUBSUB
Schema Docs : https://avro.apache.org/docs/++version++/specification/
Ref Docs :  https://cloud.google.com/pubsub/docs/schemas
Pulumi Docs: https://www.pulumi.com/registry/packages/gcp/api-docs/pubsub/schema/