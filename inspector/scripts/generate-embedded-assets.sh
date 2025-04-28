#!/bin/bash

set -e

# ==========================
# Script description
# ==========================

# This script generates a Java class containing the content of a JavaScript file as a string constant.
# It is used to embed the JavaScript file into our HTML output.
# The script is run as part of the build process to ensure that the JavaScript file is always up to date, thanks to exec-maven-plugin.

# ==========================
# Configuration
# ==========================

JS_FILE="src/main/resources/sortTable.js"
JAVA_FILE="target/generated-sources/embedded/com/theodo/inspector/utils/EmbeddedAssets.java"
PACKAGE_NAME="com.theodo.inspector.utils"
CONSTANT_NAME="SORT_TABLE_JS"

# ==========================
# Script
# ==========================

if [ ! -f "$JS_FILE" ]; then
    echo "Error : The $JS_FILE file does not exist."
    exit 1
fi

mkdir -p "$(dirname "$JAVA_FILE")"

{
echo "package $PACKAGE_NAME;"
echo ""
echo "public class EmbeddedAssets {"
echo ""
echo "    public static final String $CONSTANT_NAME = \"\"\""
} > "$JAVA_FILE"

cat "$JS_FILE" >> "$JAVA_FILE"

{
echo "\"\"\";"
echo "}"
} >> "$JAVA_FILE"

echo "✅ EmbeddedAssets.java generated in $JAVA_FILE"
