pipeline{
    agent any
    environment {
        VERSION = "${env.BUILD_ID}"
    }
    stages{
        stage("sonar quality check"){
            agent{
                docker{
                    image 'openjdk:11'
                }

            }
            steps{
                script{
                withSonarQubeEnv(credentialsId: 'sonar1') {
                    sh 'chmod +x mvnw'
                    sh './mvnw sonar:sonar -Dsonar.host.url=http://34.70.20.236:9000 -Dsonar.login=f15c3f2947551ec25927753d59883e219c5fbfa0'
                } 
                //    timeout(time: 15, unit: 'MINUTES') {
                //       def qg = waitForQualityGate()
                //       if (qg.status != 'OK') {
                //            error "Pipeline aborted due to quality gate failure: ${qg.status}"
                //       }
                //    }  
         }
            }
        
        }
    stage("Docker build & push"){
            steps{
                script{
                    withCredentials([string(credentialsId: 'nexus_repo', variable: 'docker_pass')]) {
                    sh '''
                       docker build -t 34.132.63.84:8083/maven-app:${VERSION} . 
                       docker login -u admin -p $docker_pass 34.132.63.84:8083   
                       docker push  34.132.63.84:8083/maven-app:${VERSION}
                       docker rmi 34.132.63.84:8083/maven-app:${VERSION}
                    ''' 
                    }   
                }
            }
        }
    stage("identifying misconfigs using datree in helm charts"){
        steps{
            script{
                     withEnv(['DATREE_TOKEN=1d78c93c-a3c3-42ac-bbd4-4f441a65e0c0']) {
                        
                    dir('Kubernetes/') {
                        sh 'helm datree test mvn_helm/'
                }
                     }
            }
        }
    }

    stage("Push helm chart to Nexus repo"){
            steps{
                script{
                    withCredentials([string(credentialsId: 'nexus_repo', variable: 'docker_pass')]) {
                   dir('Kubernetes/') {     
                    sh '''
                        helmversion=$(helm show chart mvn_helm | grep version | cut -d: -f 2 | tr -d  ' ')
                        tar -czvf myapp-${helmversion}.tgz mvn_helm/
                        curl -u admin:$docker_pass http://34.132.63.84:8081/repository/helm_repo/ --upload-file myapp-${helmversion}.tgz -v
                    ''' 
                    }  
                }  
                }
            }
        }
    stage("Deploy to GKE"){
        steps{
            script{
                  withCredentials([kubeconfigFile(credentialsId: 'kubeciti', variable: 'KUBECONFIG')]) {
                 dir('Kubernetes/') {      
                    sh 'helm upgrade --install --set image.repository="34.132.63.84:8083/reimburse"  myjavaapp mvn_helm/ '
                 }   
}
                }
        }
    }    

    }
    // post{
    //     always{
    //         echo "========always========"
    //     }
    //     success{
    //         echo "========pipeline executed successfully ========"
    //     }
    //     failure{
    //         echo "========pipeline execution failed========"
    //     }
    // }
}