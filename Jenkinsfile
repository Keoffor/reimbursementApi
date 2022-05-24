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
                    sh './mvnw sonar:sonar -Dsonar.host.url=http://35.224.154.122:9000 -Dsonar.login=f15c3f2947551ec25927753d59883e219c5fbfa0'
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
                    withCredentials([string(credentialsId: 'nexus-pass', variable: 'docker-pass')]) {
                    sh '''
                       docker build -t 34.121.205.170:8083/maven-app:${VERSION} . 
                       docker login -u admin -p dreams16docker login -u admin -p $docker-pass 34.121.205.170:8083   
                       docker push  34.121.205.170:8083/maven-app:${VERSION}
                       docker rmi 34.121.205.170:8083/maven-app:${VERSION}
                    ''' 
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