pipeline {
    agent any

    tools {
        maven 'Maven-3.9'
    }

    parameters {

        choice(
            name: 'SUITE_FILE',
            choices: ['all', 'testng-ui.xml', 'testng-api.xml', 'testng-db.xml'],
            description: 'Select TestNG Suite'
        )

        string(name: 'GROUPS', defaultValue: '', description: 'Run specific TestNG groups')
        string(name: 'TEST_CLASS', defaultValue: '', description: 'Run a single test class')
        string(name: 'TEST_METHOD', defaultValue: '', description: 'Run a single test method')

        choice(
            name: 'BROWSER',
            choices: ['all','chromium','webkit'],
            description: 'Select Browser'
        )

        string(name: 'THREAD_COUNT', defaultValue: '', description: 'Parallel thread count')
    }

    environment {
        REPORT_DATE = "${new Date().format('yyyy-MM-dd')}"
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

                    def mvnCmd = "mvn clean test"

                    if (params.SUITE_FILE != 'all') {
                        mvnCmd += " -Dsurefire.suiteXmlFiles=${params.SUITE_FILE}"
                    }

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
                    echo "Running Command:"
                    echo mvnCmd
                    echo "================================="

                    catchError(buildResult: 'FAILURE', stageResult: 'FAILURE') {
                        sh mvnCmd
                    }
                }

                script {

                    def baseDir = "reports/extentReports/${env.REPORT_DATE}"

                    echo "Checking generated reports..."

                    sh """
                        echo "===== EXTENT REPORT FILES ====="
                        find reports -name index.html || true
                    """

                    env.REPORT_BASE = baseDir
                }
            }
        }
    }

    post {

        always {

            script {

                def baseDir = "${env.REPORT_BASE}"

                def suites = [
                    "Automation Playwright Suite",
                    "API RestAssured Suite",
                    "DataBase Suite"
                ]

                for (suite in suites) {

                    def reportPath = "${baseDir}/${suite}"

                    sh """
                        mkdir -p "${reportPath}"

                        if [ ! -f "${reportPath}/index.html" ]; then
                            echo "<html><body><h2>No Report Generated</h2></body></html>" > "${reportPath}/index.html"
                        fi
                    """

                    publishHTML(target: [
                        reportName: "${suite}",
                        reportDir: "${reportPath}",
                        reportFiles: "index.html",
                        keepAll: true,
                        alwaysLinkToLastBuild: true,
                        allowMissing: true
                    ])
                }

                archiveArtifacts artifacts: "${baseDir}/**/*",
                allowEmptyArchive: true

                echo "Extent Reports Published Successfully"
            }
        }
    }
}