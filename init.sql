CREATE TABLE IF NOT EXISTS users (

                                     id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                     email VARCHAR(255) UNIQUE NOT NULL,
                                     password_hash VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS processing_log (
                                              id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                              user_id UUID NOT NULL,
                                              input_text TEXT NOT NULL,
                                              output_text TEXT,
                                              created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                              CONSTRAINT fk_user FOREIGN KEY(user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_users_email ON users(email);