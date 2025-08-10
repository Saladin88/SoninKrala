#!/usr/bin/env bash
set -e

: "${BACK_BASE_URL:?BACK_BASE_URL is not set}"

echo "Start of the envs modification"

# Échapper / et & pour sed (2 caractères problématiques dans la partie remplacement)
escapedBackUrl=$(printf '%s' "$BACK_BASE_URL" | sed 's/[\/&]/\\&/g')

# Remplace littéralement le token BACK_BASE_URL où qu'il soit
sed -i "s|BACK_BASE_URL|$escapedBackUrl|g" src/environments/environment.staging.ts

echo "Updated backBaseUrl to: $BACK_BASE_URL"
