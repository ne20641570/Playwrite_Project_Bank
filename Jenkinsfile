pipeline {
    agent any

    tools {
        maven 'Maven-3.9'
    }

    parameters {
        choice(name: 'SUITE_FILE', choices: ['testng-ui.xml', 'testng-api.xml'], description: 'Select TestNG suite XML')
        string(name: 'TEST_CLASS', defaultValue: '', description: 'Run a single test class')
        string(name: 'TEST_METHOD', defaultValue: '', description: 'Run a single test method')
        string(name: 'GROUPS', defaultValue: '', description: 'Run specific TestNG groups')
        choice(name: 'BROWSER', choices: ['chromium', 'webkit', 'firefox', ''], description: 'Override browser')
        string(name: 'THREAD_COUNT', defaultValue: '', description: 'Number of threads for parallel execution')
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
                    def mvnCmd = "mvn clean test -Dsurefire.suiteXmlFiles=${params.SUITE_FILE}"
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

        stage('Debug Report Folder') {
            steps {
                // Optional: List folders to check
                sh 'ls -l reports/extentReports/'
            }
        }
    }

    post {
        always {
            // Publish TestNG results
            junit '**/target/surefire-reports/*.xml'

            script {
                // Get the latest dated report folder
                def latestReport = sh(
                    script: "ls -dt reports/extentReports/* | head -1",
                    returnStdout: true
                ).trim()

                echo "Latest Extent Report folder: ${latestReport}"

                // HTML Publisher
                publishHTML(target: [
                    reportName: 'Extent Report',
                    reportDir: latestReport,
                    reportFiles: 'index.html',
                    keepAll: true,
                    alwaysLinkToLastBuild: true,
                    allowMissing: false
                ])

                // Print clickable URL in console
                def jenkinsUrl = "${env.BUILD_URL}HTML_20Report/"
                echo "Click the link to open Extent Report: ${jenkinsUrl}"
            }
        }
    }
}
