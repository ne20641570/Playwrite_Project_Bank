pipeline {
    agent { label 'mac' }  // <-- Runs on Jenkins node labeled 'mac'

    tools {
        maven 'Maven-3.9'   // Make sure Maven 3.9 is configured in Jenkins global tools on Mac node
    }

    parameters {
        choice(name: 'SUITE_FILE', choices: ['testng-ui.xml', 'testng-api.xml', 'testng-db.xml'], description: 'Select TestNG suite XML')
        string(name: 'GROUPS', defaultValue: '', description: 'Run specific TestNG groups')
        string(name: 'TEST_CLASS', defaultValue: '', description: 'Run a single test class')
        string(name: 'TEST_METHOD', defaultValue: '', description: 'Run a single test method')
        choice(name: 'BROWSER', choices: ['chromium', 'webkit'], description: 'Override browser')
        string(name: 'THREAD_COUNT', defaultValue: '', description: 'Number of threads')
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
                sh 'mvn -version'

                script {
                    def mvnCmd = "mvn clean test -Dsurefire.suiteXmlFiles=${params.SUITE_FILE}"

                    if (params.TEST_CLASS?.trim()) {
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

                    echo "================================="
                    echo "Running command:"
                    echo mvnCmd
                    echo "================================="

                    sh mvnCmd
                }
            }
        }
    }

    // Set REPORT_DIR environment variable dynamically before post
    environment {
        REPORT_DIR = "reports/extentReports/${new Date().format('yyyy-MM-dd')}"
    }

    post {
        always {
            publishHTML(target: [
                reportName: 'Extent Report',
                reportDir: env.REPORT_DIR,
                reportFiles: 'index.html',
                keepAll: true,
                alwaysLinkToLastBuild: true,
                allowMissing: false
            ])

            archiveArtifacts artifacts: "${env.REPORT_DIR}/**/*.html", allowEmptyArchive: true
        }
    }
}
