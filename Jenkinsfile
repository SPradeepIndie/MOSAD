pipeline {
    agent any

    environment{
        DB_PASSWORD = 'Idon_tknow'
		POSTGRES_HOST = '172.18.0.2'
		POSTGRES_PORT = '5432'
		DB_USERNAME = 'postgres'
    }

    stages {
        stage('compile') {
            steps {
                sh "mvn clean compile"
            }
        }
        stage('deploy') {
            steps {
                echo "deploying"
            }
        }
        
        
    }
}
