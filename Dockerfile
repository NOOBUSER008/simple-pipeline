# Use an official Nginx image as a parent image
FROM nginx

# Copy the index.html file into the container at /usr/share/nginx/html
COPY index.html /usr/share/nginx/html

# Expose port 80 for the web server to listen on
EXPOSE 80

# Start Nginx when the container launches
CMD ["nginx", "-g", "daemon off;"]
