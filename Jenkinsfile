pipeline {
    agent any

    tools {
        maven 'Maven-3.9'
    }

    parameters {
        choice(name: 'SUITE_FILE',
            choices: ['all', 'testng-ui.xml', 'testng-api.xml', 'testng-db.xml'],
            description: 'Select TestNG suite XML')

        string(name: 'GROUPS', defaultValue: '', description: 'Run specific TestNG groups')
        string(name: 'TEST_CLASS', defaultValue: '', description: 'Run a single test class')
        string(name: 'TEST_METHOD', defaultValue: '', description: 'Run a single test method')

        choice(name: 'BROWSER',
            choices: ['all','chromium', 'webkit'],
            description: 'Override browser')

        string(name: 'THREAD_COUNT', defaultValue: '', description: 'Number of threads')
    }

    environment {
        EMAIL_RECIPIENTS = "your_email@example.com"
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/ne20641570/Playwrite_Project_Bank.git',
                    credentialsId: 'github-creds'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Run Tests') {
            steps {
                script {
                    // Build Maven command dynamically
                    def mvnCmd = "mvn clean test -Dsurefire.suiteXmlFiles=${params.SUITE_FILE}"

                    if (params.GROUPS?.trim()) {
                        mvnCmd += " -Dgroups=${params.GROUPS}"
                    }
                    if (params.TEST_CLASS?.trim()) {
                        mvnCmd += " -Dtest=${params.TEST_CLASS}"
                    }
                    if (params.TEST_METHOD?.trim()) {
                        mvnCmd += " -Dtest=${params.TEST_METHOD}"
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

                    catchError(buildResult: 'FAILURE', stageResult: 'FAILURE') {
                        sh mvnCmd
                    }

                    // Determine report folder based on suite
                    def reportDate = new Date().format('yyyy-MM-dd')
                    def reportBaseDir = "reports/extentReports/${reportDate}"
                    def suiteFolder = ""

                    switch(params.SUITE_FILE) {
                        case 'testng-ui.xml':
                            suiteFolder = "Automation-Playwright-Suite"
                            break
                        case 'testng-api.xml':
                            suiteFolder = "API-RestAssured-Suite"
                            break
                        case 'testng-db.xml':
                            suiteFolder = "DataBase-Suite"
                            break
                        default:
                            suiteFolder = "All-Suites"
                    }

                    def fullReportDir = "${reportBaseDir}/${suiteFolder}"
                    env.REPORT_DIR = fullReportDir

                    // Ensure the folder exists
                    sh """
                        if [ -d "${fullReportDir}" ]; then
                            echo "Report folder exists: ${fullReportDir}"
                            ls -l ${fullReportDir}
                        else
                            echo "Report folder NOT found: ${fullReportDir}"
                        fi
                    """
                }
            }
        }
    }

    post {
        always {
            publishHTML(target: [
                reportName: "ExtentReport_${params.SUITE_FILE}",
                reportDir: "${env.REPORT_DIR}",
                reportFiles: "index.html",
                keepAll: true,
                alwaysLinkToLastBuild: true,
                allowMissing: false
            ])

            archiveArtifacts artifacts: "${env.REPORT_DIR}/**/*",
                             allowEmptyArchive: true

            echo "Extent Report URL:"
            echo "${env.BUILD_URL}Extent_Report_${params.SUITE_FILE}/"
        }
    }
}