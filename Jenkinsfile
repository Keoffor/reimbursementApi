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
                withSonarQubeEnv(credentialsId: 'jenkins-sonar1') {
                    sh 'chmod +x mvnw'
                    sh './mvnw sonarqube'
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