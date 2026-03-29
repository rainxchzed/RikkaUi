#!/bin/bash
set -e

REPO="rainxchzed/RikkaUi"
INSTALL_DIR="${RIKKAUI_INSTALL_DIR:-$HOME/.rikkaui}"
BIN_NAME="rikkaui"

echo "Installing RikkaUI CLI..."

# Get latest release tag
TAG=$(curl -fsSL "https://api.github.com/repos/$REPO/releases/latest" | grep '"tag_name"' | head -1 | cut -d'"' -f4)
if [ -z "$TAG" ]; then
    echo "Error: Could not determine latest release."
    exit 1
fi

echo "Latest version: $TAG"

JAR_URL="https://github.com/$REPO/releases/download/$TAG/rikkaui.jar"

mkdir -p "$INSTALL_DIR"

echo "Downloading rikkaui.jar..."
curl -fsSL "$JAR_URL" -o "$INSTALL_DIR/rikkaui.jar"

# Create wrapper script
cat > "$INSTALL_DIR/$BIN_NAME" << 'WRAPPER'
#!/bin/bash
exec java -jar "$(dirname "$0")/rikkaui.jar" "$@"
WRAPPER
chmod +x "$INSTALL_DIR/$BIN_NAME"

# Add to PATH if needed
SHELL_CONFIG=""
if [ -f "$HOME/.zshrc" ]; then
    SHELL_CONFIG="$HOME/.zshrc"
elif [ -f "$HOME/.bashrc" ]; then
    SHELL_CONFIG="$HOME/.bashrc"
fi

if [ -n "$SHELL_CONFIG" ]; then
    if ! grep -q "$INSTALL_DIR" "$SHELL_CONFIG" 2>/dev/null; then
        echo "" >> "$SHELL_CONFIG"
        echo "# RikkaUI CLI" >> "$SHELL_CONFIG"
        echo "export PATH=\"$INSTALL_DIR:\$PATH\"" >> "$SHELL_CONFIG"
        echo "Added $INSTALL_DIR to PATH in $SHELL_CONFIG"
        echo "Run 'source $SHELL_CONFIG' or restart your terminal."
    fi
fi

echo ""
echo "  RikkaUI CLI installed to $INSTALL_DIR/$BIN_NAME"
echo ""
echo "  Usage:"
echo "    rikkaui init"
echo "    rikkaui add button dialog card"
echo "    rikkaui list"
echo ""
