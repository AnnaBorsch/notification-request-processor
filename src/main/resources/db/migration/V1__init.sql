CREATE TABLE IF NOT EXISTS messages (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    type VARCHAR(50) NOT NULL,
    message TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS notification_outbox (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    topic VARCHAR(100) NOT NULL,
    message_key VARCHAR(36) NOT NULL,
    value TEXT NOT NULL,
    sent BOOLEAN NOT NULL DEFAULT FALSE,
    attempt_count INT NOT NULL DEFAULT 0
);

CREATE INDEX idx_outbox_unsent ON notification_outbox(sent, created_at) WHERE sent = FALSE;
CREATE INDEX idx_outbox_key ON notification_outbox(message_key);