pipeline {
    agent any

    tools {
        maven 'Maven-3.9'
    }

    parameters {
        choice(name: 'SUITE_FILE', choices: ['testng-ui.xml', 'testng-api.xml','testng-db.xml'], description: 'Select TestNG suite XML')
        string(name: 'TEST_CLASS', defaultValue: '', description: 'Run a single test class (optional, e.g., RegistrationTest)')
        string(name: 'TEST_METHOD', defaultValue: '', description: 'Run a single test method from a class (optional, e.g., RegistrationTest#registrationPageValidation)')
        string(name: 'GROUPS', defaultValue: '', description: 'Run specific TestNG groups (optional, e.g., Registration, Login)')
        choice(name: 'BROWSER', choices: ['chromium', 'webkit', ''], description: 'Override browser for UI tests (optional)')
        string(name: 'THREAD_COUNT', defaultValue: '', description: 'Number of threads for parallel execution (optional)')
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/ne20641570/Playwrite_Project_Bank.git',
                    credentialsId: 'github-creds'
            }
        }

        stage('Run Tests') {
            steps {
                script {
                    // Base Maven command
                    def mvnCmd = "mvn clean test -Dsurefire.suiteXmlFiles=${params.SUITE_FILE}"

                    // Add optional parameters
                    if (params.TEST_METHOD?.trim()) {
                        mvnCmd += " -Dtest=${params.TEST_METHOD}"
                    } else if (params.TEST_CLASS?.trim()) {
                        mvnCmd += " -Dtest=${params.TEST_CLASS}"
                    }

                    if (params.GROUPS?.trim()) {
                        mvnCmd += " -Dgroups=${params.GROUPS}"
                    }

                    if (params.BROWSER?.trim()) {
                        mvnCmd += " -Dbrowser=${params.BROWSER}"
                    }

                    if (params.THREAD_COUNT?.trim()) {
                        mvnCmd += " -Dthread.count=${params.THREAD_COUNT}"
                    }

                    echo "Running command: ${mvnCmd}"
                    sh mvnCmd
                }
            }
        }
    }

    post {
        always {
            // Assuming Maven Surefire / Failsafe reports
            junit '**/target/surefire-reports/*.xml'
        }
    }
}
