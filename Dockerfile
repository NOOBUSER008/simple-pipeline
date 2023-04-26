# Use an official Nginx image as a parent image
FROM nginx

# Install curl
RUN apt-get update && apt-get install -y curl

# Fetch the index.html file from GitHub and copy it into the container at /usr/share/nginx/html
RUN curl -LJO https://raw.githubusercontent.com/NOOBUSER008/simple-pipeline/main/index.html \
    && mv index.html /usr/share/nginx/html

# Expose port 80 for the web server to listen on
EXPOSE 80

# Start Nginx when the container launches
CMD ["nginx", "-g", "daemon off;"]

