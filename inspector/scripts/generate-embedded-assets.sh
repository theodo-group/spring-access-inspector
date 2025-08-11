#!/bin/bash

set -e

# ==========================
# Script description
# ==========================

# This script generates a Java class containing the content of all JavaScript files in the src/main/resources folder as string constants.
# It is used to embed the JavaScript files into our HTML output.
# The script is run as part of the build process to ensure that the JavaScript files are always up to date, thanks to exec-maven-plugin.

# ==========================
# Configuration
# ==========================

JS_FOLDER="src/main/resources"
JAVA_FILE="target/generated-sources/embedded/com/theodo/inspector/utils/EmbeddedAssets.java"
PACKAGE_NAME="com.theodo.inspector.utils"

# ==========================
# Script
# ==========================

if [ ! -d "$JS_FOLDER" ]; then
    echo "Error: The $JS_FOLDER folder does not exist."
    exit 1
fi

mkdir -p "$(dirname "$JAVA_FILE")"

{
echo "package $PACKAGE_NAME;"
echo ""
echo "public class EmbeddedAssets {"
echo ""
} > "$JAVA_FILE"

# Iterate over all JavaScript files in the folder
for js_file in "$JS_FOLDER"/*.js; do
    if [ -f "$js_file" ]; then
        # Convert file name to uppercase with underscores between words
        constant_name=$(basename "$js_file" .js | sed -E 's/([a-z])([A-Z])/\1_\2/g' | tr '[:lower:]' '[:upper:]' | tr '-' '_' | tr '.' '_')_JS
        {
        echo "    public static final String $constant_name = \"\"\""
        # Double backslashes so Java doesn't treat things like \? as escapes
        sed 's/\\/\\\\/g' "$js_file"
        echo "\"\"\";"
        echo ""
        } >> "$JAVA_FILE"
    fi
done

{
echo "}"
} >> "$JAVA_FILE"

echo "✅ EmbeddedAssets.java generated in $JAVA_FILE"
