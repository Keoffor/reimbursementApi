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
                    sh './mvnw sonar:sonar -Dsonar.host.url=http://34.123.46.30:9000 -Dsonar.login=f15c3f2947551ec25927753d59883e219c5fbfa0'
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
                       docker build -t 35.184.63.1:8083/maven-app:${VERSION} . 
                       docker login -u admin -p $docker_pass 35.184.63.1:8083   
                       docker push  35.184.63.1:8083/maven-app:${VERSION}
                       docker rmi 35.184.63.1:8083/maven-app:${VERSION}
                    ''' 
                    }   
                }
            }
        }
    // stage("identifying misconfigs using datree in helm charts"){
    //     steps{
    //         script{

    //         }
    //     }
    // }

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