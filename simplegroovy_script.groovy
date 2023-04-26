pipeline {
    agent any
    stages {
        stage('Build and Test') {
            steps {
                sh 'npm install' // Install dependencies
                sh 'npm run test' // Run tests
            }
        }
        stage('Build Docker Image') {
            steps {
                script {
                    docker.build("my-simple-image:${env.BUILD_NUMBER}")
                    docker.withRegistry('https://123456789012.dkr.ecr.region.amazonaws.com', 'ecr:ap-south-1:AKIA2RE5GGT4HIZSQ3NN', 'XzZMWqjGGyW/o/SeiWKgweP7Bg9UrQpQizQ5bpXE') {
                        docker.push("my-simple-image:${env.BUILD_NUMBER}")
                    }
                }
            }
        }
        stage('Deploy Docker Image') {
            steps {
                sshagent(['phani123']) { // Use SSH credentials to connect to EC2 instance
                    sh "ssh -o StrictHostKeyChecking=no -l admin 3.6.94.231 'docker pull 123456789012.dkr.ecr.region.amazonaws.com/my-simple-image:${env.BUILD_NUMBER}'" // Pull Docker image from ECR
                    sh "ssh -o StrictHostKeyChecking=no -l admin 3.6.94.231 'docker run -d --name my-container -p 80:80 123456789012.dkr.ecr.region.amazonaws.com/my-simple-image:${env.BUILD_NUMBER}'" // Start Docker container on EC2 instance
                }
            }
        }
    }
    post {
        success {
            mail to: 'phanimathangi98@gmail.com',
                subject: "Build ${env.BUILD_NUMBER} succeeded",
                body: "The build succeeded"
        }
        failure {
            mail to: 'phanimathangi98@gmail.com.com',
                subject: "Build ${env.BUILD_NUMBER} failed",
                body: "The build failed"
        }
    }
}
