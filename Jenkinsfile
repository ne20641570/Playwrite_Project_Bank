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
                    // 1️⃣ Build Maven command dynamically
                    def mvnCmd = "mvn clean test -Dsurefire.suiteXmlFiles=${params.SUITE_FILE}"

                    if (params.GROUPS?.trim()) mvnCmd += " -Dgroups=${params.GROUPS}"
                    if (params.TEST_CLASS?.trim()) mvnCmd += " -Dtest=${params.TEST_CLASS}"
                    if (params.TEST_METHOD?.trim()) mvnCmd += " -Dtest=${params.TEST_METHOD}"
                    if (params.BROWSER?.trim()) mvnCmd += " -Dbrowser=${params.BROWSER}"
                    if (params.THREAD_COUNT?.trim()) mvnCmd += " -Dthread.count=${params.THREAD_COUNT}"

                    echo "================================="
                    echo "Running Maven command:"
                    echo mvnCmd
                    echo "================================="

                    catchError(buildResult: 'FAILURE', stageResult: 'FAILURE') {
                        sh mvnCmd
                    }

                    // 2️⃣ Automatically detect the Extent report folder
                    def reportDirCandidate = sh(
                        script: "find target/ -type d -name '*ExtentReports*' | head -n 1",
                        returnStdout: true
                    ).trim()

                    if (!reportDirCandidate) {
                        error("❌ Extent report folder not found! Check your Maven/Playwright project configuration.")
                    }

                    env.REPORT_DIR = reportDirCandidate
                    echo "✅ Detected Extent report directory: ${env.REPORT_DIR}"
                }
            }
        }
    }

    post {
        always {
            script {
                // 3️⃣ Publish HTML report
                publishHTML(target: [
                    reportName: "ExtentReport_${params.SUITE_FILE}",
                    reportDir: "${env.REPORT_DIR}",
                    reportFiles: "index.html",
                    keepAll: true,
                    alwaysLinkToLastBuild: true,
                    allowMissing: false
                ])

                // 4️⃣ Archive all report artifacts
                archiveArtifacts artifacts: "${env.REPORT_DIR}/**/*",
                                 allowEmptyArchive: true

                echo "Extent Report URL:"
                echo "${env.BUILD_URL}Extent_Report_${params.SUITE_FILE}/"
            }
        }
    }
}