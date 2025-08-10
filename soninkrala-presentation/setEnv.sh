echo "Start of the envs modification"
soninkralaBackBaseUrl=${BACK_BASE_URL}
escapedBackUrl=$(printf '%s\n' "$soninkralaBackBaseUrl" | sed -e 's/[\/&]/\\&/g')

sed -i "s|backBaseUrl : .*|backBaseUrl : '${escapedBackUrl}'|" src/environments/environment.staging.ts

echo "backBaseUrl updated with : $escapedBackUrl"