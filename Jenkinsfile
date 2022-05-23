pipeline{
    agent any
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
                    sh './mvnw sonar:sonar -Dsonar.host.url=http://34.132.185.161:9000 -Dsonar.login=f15c3f2947551ec25927753d59883e219c5fbfa0'
                } 
                   timeout(time: 15, unit: 'MINUTES') {
                      def qg = waitForQualityGate()
                      if (qg.status != 'OK') {
                           error "Pipeline aborted due to quality gate failure: ${qg.status}"
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