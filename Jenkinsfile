pipeline {
	echo "Starting Pipeline Execution..."
    agent any  // Runs on Jenkins node labeled 'mac'

    tools {
        maven 'Maven-3.9'
    }

    parameters {
		echo "Defining Pipeline Parameters..."
        choice(name: 'SUITE_FILE', choices: ['testng-ui.xml', 'testng-api.xml', 'testng-db.xml'], description: 'Select TestNG suite XML')
        string(name: 'GROUPS', defaultValue: '', description: 'Run specific TestNG groups')
        string(name: 'TEST_CLASS', defaultValue: '', description: 'Run a single test class')
        string(name: 'TEST_METHOD', defaultValue: '', description: 'Run a single test method')
        choice(name: 'BROWSER', choices: ['chromium', 'webkit'], description: 'Override browser')
        string(name: 'THREAD_COUNT', defaultValue: '', description: 'Number of threads')
    }

    environment {
		echo "Setting Environment Variables..."
        REPORT_DIR = "reports/extentReports/${new Date().format('yyyy-MM-dd')}"
    }

    stages {
		echo "Defining Pipeline Stages..."
        stage('Checkout') {
            steps {
				echo "Checking out source code from GitHub..."
                git branch: 'main',
                    url: 'https://github.com/ne20641570/Playwrite_Project_Bank.git',
                    credentialsId: 'github-creds'
            }
        }

        stage('Run Tests') {
            steps {
                sh '''
                    echo "Preparing shell environment..."
                    touch .bash_profile
                    source ~/.bash_profile

                    echo "Maven version:"
                    mvn -version
                '''

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

            echo "Extent Report URL:"
            echo "${env.BUILD_URL}artifact/${env.REPORT_DIR}/index.html"
        }
    }
}
